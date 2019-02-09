var ajaxUrl = 'handbk/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "name"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderTranslationBtn
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
});

function addHandbk() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find("#id").val("");
    form.find("#name").val("");
    $('#editRow').modal();
}

function renderTranslationBtn(data, type, row) {
    var handbkParameter = '?handbk=' + $('#handbkParameter').val();
    if (type === 'display') {
        return "<a href='translation" + handbkParameter + "&handbkItemId=" + row.id + "'>"
                                                                             + i18n['translation']
                                                                             + '</a>';
    }
}
