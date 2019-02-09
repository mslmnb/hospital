var ajaxUrl = 'translation/';
var datatableApi;

// function updateTable() {
//     $.get(ajaxUrl + "all", updateTableByData);
// }

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
        drawLocaleOptions(data.locale);
    });
    $('#editRow').modal();
}

function addTranslation() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find("#id").val("");
    form.find("#locale").val("");
    form.find("#translation").val("");
    drawLocaleOptions();
    $('#editRow').modal();
}

function drawLocaleOptions(locale) {
    $.ajax({
        type: "GET",
        url: "lang/all",
        success: function (data) {
            var localeSelect = $("#locale");
            localeSelect.empty();
            var option = $("<option>");
            option.attr("disabled", true)
                .html(i18n["selectLocale"])
                .appendTo(localeSelect);
            if (locale == undefined) {
                option.attr("selected", true);
            }
            for (choice in data) {
                var option = $("<option>");
                option.val(data[choice].locale)
                    .html(data[choice].locale)
                    .appendTo(localeSelect)
                if(data[choice].locale == locale) {
                    option.attr("selected", true)
                }
            }
        }
    });
}


