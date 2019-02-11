function makeEditable() {
    form = $('#detailsForm');
    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
}

function add() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(":input").val("");
    $('#editRow').modal();
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function updateTable() {
    var requestParameter = $('#requestParameter').val();
    requestParameter = requestParameter == undefined ? '' : requestParameter;
    $.get(ajaxUrl + "all" + requestParameter, updateTableByData);
}


function save() {
    var form = $('#detailsForm');
    form.find(':input:disabled').addClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $.ajax({
        type: "POST",
        url: ajaxUrl + "save",
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('common.saved');
        },
        error: function () {
            form.find('.disabled').attr('disabled', true);
        }
    });
}
function extendsOpts(opts) {
    var requestParameter = $('#requestParameter').val();
    requestParameter = requestParameter == undefined ? '' : requestParameter;
    $.extend(true, opts,
        {
            "ajax": {
                "url": ajaxUrl + "all" + requestParameter,
                "dataSrc": ""
            },
            "paging": true,
            "info": true,
            "language": {
                "search": i18n["common.search"],
                "lengthMenu": i18n["common.lengthMenu"],
                "paginate": {
                    "first":      i18n["common.first"],
                    "last":       i18n["common.last"],
                    "next":       i18n["common.next"],
                    "previous":   i18n["common.previous"]
                },
            },
            "initComplete": makeEditable
        });
    return opts;
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    noty({
        text: i18n[key],
        type: 'success',
        layout: 'bottomRight',
        timeout: 1500
    });
}

function failNoty(jqXHR) {
    closeNoty();
    var errorInfo = $.parseJSON(jqXHR.responseText);
    var keys = errorInfo.details.map(function (detail) {
        return i18n[detail]
    })
    failedNote = noty({
        text: keys.join("<br>"),
        type: 'error',
        layout: 'bottomRight'
    });
}

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + "delete?id=" + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('common.deleted');
        }
    });
}

function renderEditBtn(data, type, row) {
    if (type === 'display') {
        return '<a onclick="updateRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === 'display') {
        return '<a onclick="deleteRow(' + row.id + ');">'+
            '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>';
    }
}

