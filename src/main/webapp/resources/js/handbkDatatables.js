var ajaxUrl = 'handbk/';
var datatableApi;

// function updateTable() {
//     $.get(ajaxUrl + "all?handbk=POSITION", updateTableByData);
// }

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

function renderTranslationBtn(data, type, row) {
    if (type === 'display') {
        return '<a href="">' + i18n['translation'] + '</a>';
    }
}
