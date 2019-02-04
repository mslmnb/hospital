var ajaxUrl = 'users/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl + "all", updateTableByData);
}

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

function addUser() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(":input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    // $('#login').removeAttribute("disabled");
    // $('#staffId').removeAttribute("disabled");
    // $('#role').removeAttribute("disabled");
    drawStaffOptions();
    drawRoleOptions();
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
        $("#login").attr("value",data.login);
        $("#role").attr("value",data.role);
        drawStaffOptions(data.staffId);
        drawRoleOptions(data.role);
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
                    .html(data[choice].name)
                    .appendTo(staffSelect)
                if(data[choice].id == staffId) {
                        option.attr("selected", true)
                }
            }
        }
    });
}

function drawRoleOptions(role) {
    $.ajax({
        type: "GET",
        url: "role/all",
        success: function (data) {
            var roleSelect = $("#role");
            roleSelect.empty();
            var option = $("<option>")
            option.attr("disabled", true)
                .html(i18n["selectRole"])
                .appendTo(roleSelect);
            if (role == undefined) {
                option.attr("selected", true);
            }
            for (choice in data) {
                var option = $("<option>");
                option.val(data[choice].role)
                    .html(data[choice].role)
                    .appendTo(roleSelect)
                if (data[choice].role == role) {
                    option.attr("selected", true);
                }
            }
        }
    });
}








