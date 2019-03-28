var ajaxUrl = 'users/';
var datatableApi;

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a onclick="updatePassword(' + row.id + ');">' +
            i18n["editPassword"] + '</a>';
    }
}


$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "staffName"
            },
            {
                "data": "login"
            },
            {
                "data": "role"
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

function addUser() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(":input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    drawStaffOptions();
    drawOptions("#role", "role/all", "selectRole");
    $('#editRow').modal();
}

function updatePassword(id) {
    $('#modalTitle').html(i18n["editPassword"]);
    form.find(":input").val("");
    $('#login').attr("disabled", true);
    $('#staffId').attr("disabled", true);
    $('#role').attr("disabled", true);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $("#id").val(data.id);
        $("#login").val(data.login);
        drawStaffOptions(data.staffId);
        drawOptions("#role", "role/all", "selectRole", data.role);
    });
    $('#editRow').modal();
}

function drawStaffOptions(staffId) {
    $.ajax({
        type: "GET",
        url: "staff/all",
        success: function (data) {
            var staffSelect = $("#staffId");
            staffSelect.empty();
            var option = $("<option>");
            option.attr("disabled", true)
                .html(i18n["selectStaff"])
                .appendTo(staffSelect);
            if (staffId == undefined) {
                option.attr("selected", true);
            }
            for (choice in data) {
                var option = $("<option>");
                option.val(data[choice].id)
                    .html(data[choice].surname + " " + data[choice].name + " " + data[choice].additionalName)
                    .appendTo(staffSelect);
                if(data[choice].id == staffId) {
                        option.attr("selected", true)
                }
            }
        }
    });
}









