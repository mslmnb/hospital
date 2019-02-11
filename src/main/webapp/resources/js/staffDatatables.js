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
        drawPositionOptions(data.positionId);
    });
    $('#editRow').modal();
}

function addStaff() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(":input").val("");
    drawPositionOptions();
    $('#editRow').modal();
}

function drawPositionOptions(positionId) {
    $.ajax({
        type: "GET",
        url: "handbk/translation?type=position",
        success: function (data) {
            var positionSelect = $("#positionId");
            positionSelect.empty();
            var option = $("<option>");
            option.attr("disabled", true)
                .html(i18n["selectPosition"])
                .appendTo(positionSelect);
            if (positionId == undefined) {
                option.attr("selected", true);
            }
            for (choice in data) {
                var option = $("<option>");
                option.val(data[choice].id)
                    .html(data[choice].name)
                    .appendTo(positionSelect)
                if(data[choice].id == positionId) {
                    option.attr("selected", true)
                }
            }
        }
    });
}


