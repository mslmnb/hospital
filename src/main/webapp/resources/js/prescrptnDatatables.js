var ajaxUrl = 'prescription/';
var datatableApi;

function add() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(".input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $('#executionDate').attr("disabled", true);
    $('#result').attr("disabled", true);
    drawOptions("#typeId", "handbk/translation?type=prescrptn_type", "selectPrescrptnType");
    $('#editRow').modal();
}

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    form.find(".input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $('#executionDate').attr("disabled", true);
    $('#result').attr("disabled", true);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
        });
        drawOptions("#typeId", "handbk/translation?type=prescrptn_type", "selectPrescrptnType", data.typeId);
        $('#editRow').modal();
    });
}

function updateExecutionRow(id) {
    $('#modalTitle').html(i18n["executionEditTitle"]);
    form.find(".input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $('#applicationDate').attr("disabled", true);
    $('#typeId').attr("disabled", true);
    $('#description').attr("disabled", true);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
        });
        drawOptions("#typeId", "handbk/translation?type=prescrptn_type", "selectPrescrptnType", data.typeId);
        $('#editRow').modal();
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "applicationDate"
            },
            {
                "data": "type"
            },
            {
                "data": "description"
            },
            {
                "data": "executionDate"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderExecuteBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0, "asc"
            ]
        ]
    }));
    $.datetimepicker.setLocale(localeCode);

    $('#applicationDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

    $('#executionDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })
});

function renderExecuteBtn(data, type, row) {
    if (type == 'display') {
        return '<a onclick="updateExecutionRow(' + row.id + ');">'
                                                          + i18n['execution'] + '</a>';
    }
}

