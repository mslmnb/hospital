var ajaxUrl = 'patients/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "fullname"
            },
            {
                "data": "admissionDate"
            },
            {
                "data": "dischargeDate"
            },
            {
                "defaultContent": "",
                "render": renderPrimaryExamBtn
            },
            {
                "defaultContent": "",
                "render": renderDiagnosisBtn
            },
            {
                "defaultContent": "",
                "render": renderPrescriptionBtn
            },
            {
                "defaultContent": "",
                "render": renderInspectionBtn
            },
            {
                "defaultContent": "",
                "render": renderDischargeBtn
            }

        ]
    }));
    $.datetimepicker.setLocale(localeCode);

    $('#dischargeDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })
});

function updatePrimaryExam(id) {
    update(id, "editPrimaryExamTitle", false);
}

function updateDischarge(id) {
    update(id, "editDischargeTitle", true);
}

function update(id, title, disabled) {
    $('#modalTitle').html(i18n[title]);
    form.find(":input").val("");
    form.find('.disabled').removeClass('disabled');
    $('#primaryComplaints').attr("disabled", disabled);
    $('#primaryInspection').attr("disabled", disabled);
    $('#primaryDiagnosisId').attr("disabled", disabled);
    $('#dischargeDate').attr("disabled", !disabled);
    $('#finalDiagnosisId').attr("disabled", !disabled);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
        });
        drawOptions("#primaryDiagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.primaryDiagnosisId);
        drawOptions("#finalDiagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.finalDiagnosisId);
    });
    $('#editRow').modal();
}

function renderPrimaryExamBtn(data, type, row) {
    if (type === 'display') {
        var disabledClass = "class='disabled'";
        var onClick = "";
        if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
            disabledClass = "";
            onClick = "'updatePrimaryExam(" + row.id + ");'";
        }
        return "<a " + disabledClass + "' onclick=" + onClick + ">" + i18n["primaryExamBtn"] + "</a>";
    }
}

function renderDiagnosisBtn(data, type, row) {
    if (type === 'display') {
        var disabledClass = " disabled";
        var href = "";
        if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
            disabledClass = "";
            href = "'diagnosis?id=" + row.id + "&name=" + getFullName(row) + "'";
        }
        return "<a class='btn btn-xs btn-primary" + disabledClass + "' href=" + href + ">"
                                                                  + i18n["diagnosisBtn"] + "</a>";
    }
}

function renderPrescriptionBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='prescription?id=" + row.id
                                                                          + "&name=" + getFullName(row) + "'>"
                                                                          + i18n["prescriptionBtn"]
                                                                          + "</a>";
    }
}

function renderInspectionBtn(data, type, row) {
    if (type === 'display') {
        var disabledClass = " disabled";
        var href = "";
        if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
            disabledClass = "";
            href = "'inspection?id=" + row.id + "&name=" + getFullName(row) + "'";
        }
        return "<a class='btn btn-xs btn-primary"+ disabledClass + "' href=" + href +  ">"
                                                                 + i18n["inspectionBtn"] + "</a>";
    }
}

function renderDischargeBtn(data, type, row) {
    if (type === 'display') {
        var disabledClass = "class='disabled'";
        var onClick = "";
        if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
            disabledClass = "";
            onClick = "'updateDischarge(" + row.id + ");'";
        }
        return "<a " + disabledClass + "' onclick=" + onClick + ">" + i18n["dischargeBtn"] + "</a>";
    }
}

function getFullName(row) {
    return row.surname + " " + row.name + " " + row.additionalName;
}