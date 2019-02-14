var ajaxUrl = 'staff/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "additionalName"
            },
            {
                "data": "surname"
            },
            {
                "data": "position"
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
    form.find(":input").val("");
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $("#id").val(data.id);
        $("#name").val(data.name);
        $("#additionalName").val(data.additionalName);
        $("#surname").val(data.surname);
        drawOptions("#positionId", "handbk/translation?type=position", "selectPosition", data.positionId);
    });
    $('#editRow').modal();
}

function addStaff() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(":input").val("");
    drawOptions("#positionId", "handbk/translation?type=position", "selectPosition");
    $('#editRow').modal();
}


