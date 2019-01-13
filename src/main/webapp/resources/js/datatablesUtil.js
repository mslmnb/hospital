function makeEditable() {
    i = 0;
    // $('.delete').click(function () {
    //     deleteRow($(this).attr("id"));
    // });
    //
    // $('#detailsForm').submit(function () {
    //     save();
    //     return false;
    // });
    //
    // $(document).ajaxError(function (event, jqXHR, options, jsExc) {
    //     failNoty(event, jqXHR, options, jsExc);
    // });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}
function extendsOpts(opts) {
    $.extend(true, opts,
        {
            "ajax": {
                "url": ajaxUrl,
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

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: 1500
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
