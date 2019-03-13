var ajaxUrl = 'handbk/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "name"
            },
            {
                "defaultContent": "",
                "render": renderTranslationBtn
            },
            {
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ]
    }));
});

function addHandbk() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find("#id").val("");
    form.find("#name").val("");
    $('#editRow').modal();
}

function renderTranslationBtn(data, type, row) {
    var requestParameter = $('#requestParameter').val();
    if (type === 'display') {
        return "<a href='translation" + requestParameter + "&id=" + row.id + "&name=" + row.name + "'>"
                                                                           + i18n['translation'] + '</a>';
    }
}
