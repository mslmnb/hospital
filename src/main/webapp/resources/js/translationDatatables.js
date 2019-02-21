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
    form.find(".input").val("");
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
        });
        drawOptions("#locale", "lang/all", "selectLocale", data.locale);
    });
    $('#editRow').modal();
}

function addTranslation() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(".input").val("");
    drawOptions("#locale", "lang/all", "selectLocale");
    $('#editRow').modal();
}



