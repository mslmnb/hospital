var ajaxUrl = 'translation/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "locale"
            },
            {
                "data": "translation"
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

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    // form.find(":input").val("");
    form.find("#id").val("");
    form.find("#locale").val("");
    form.find("#translation").val("");
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $("#id").val(data.id);
        $("#locale").val(data.locale);
        $("#translation").val(data.translation);
        drawOptions("#locale", "lang/all", "selectLocale", data.locale);
    });
    $('#editRow').modal();
}

function addTranslation() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(".entry-field").val("");
    drawOptions("#locale", "lang/all", "selectLocale");
    $('#editRow').modal();
}



