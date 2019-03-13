var ajaxUrl = 'prescription/';
var datatableApi;

function add() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(".input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $('#executionDate').attr("disabled", true);
    $('#result').attr("disabled", true);
    drawOptions("#typeId", "handbk/translation?type=prescrptn_type", "selectPrescrptnType");
    $('#editRow').modal();
}

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    form.find(".input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $('#executionDate').attr("disabled", true);
    $('#result').attr("disabled", true);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
        });
        drawOptions("#typeId", "handbk/translation?type=prescrptn_type", "selectPrescrptnType", data.typeId);
        $('#editRow').modal();
    });
}

function updateExecutionRow(id) {
    $('#modalTitle').html(i18n["executionEditTitle"]);
    form.find(".input").val("");
    form.find('.disabled').removeClass('disabled');
    form.find(':input:disabled').removeAttr('disabled');
    $('#applicationDate').attr("disabled", true);
    $('#typeId').attr("disabled", true);
    $('#description').attr("disabled", true);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
        });
        drawOptions("#typeId", "handbk/translation?type=prescrptn_type", "selectPrescrptnType", data.typeId);
        $('#editRow').modal();
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "applicationDate"
            },
            {
                "data": "type"
            },
            {
                "data": "description"
            },
            {
                "data": "executionDate"
            },
            {
                "defaultContent": "",
                "render": renderExecuteBtn
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
    $.datetimepicker.setLocale(localeCode);

    $('#applicationDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

    $('#executionDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

    if ($('#authorizedUserRole').val() == "ROLE_NURSE") {
        $("#addButton").addClass("disabled");
        $("#addButton").attr("href", "");
    }
});

function renderExecuteBtn(data, type, row) {
    if (type == 'display') {
        return '<a onclick="updateExecutionRow(' + row.id + ');">'
            + i18n['execution'] + '</a>';
    }
}

function renderEditBtn(data, type, row) {
    if (type === 'display') {
        var disabledClass = "class='disabled'";
        var onClick = "";
        if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
            disabledClass = "";
            onClick = "'updateRow(" + row.id + ");'";
        }
        return "<a " + disabledClass + " onclick=" + onClick + ">"
                                     + "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === 'display') {
        var disabledClass = "class='disabled'";
        var onClick = "";
        if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
            disabledClass = "";
            onClick = "'deleteRow(" + row.id + ");'";
        }
        return "<a " + disabledClass + " onclick=" + onClick + ">"
                                     + "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>";
    }
}



