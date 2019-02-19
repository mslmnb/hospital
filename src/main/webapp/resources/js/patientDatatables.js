var ajaxUrl = 'patients/';
var datatableApi;

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "surname"
            },
            {
                "data": "name"
            },
            {
                "data": "additionalName"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderPrimaryExamBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDiagnosisBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderPrescriptionBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderInspectionBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDischargeBtn
            }

        ],
        "order": [
            [
                0, "asc"
            ]
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
    $('#modalTitle').html(i18n["editPrimaryExamTitle"]);
    form.find(":input").val("");
    form.find('.disabled').removeClass('disabled');
    $('#primaryComplaints').attr("disabled", false);
    $('#primaryInspection').attr("disabled", false);
    $('#primaryDiagnosisId').attr("disabled", false);
    $('#dischargeDate').attr("disabled", true);
    $('#finalDiagnosisId').attr("disabled", true);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#primaryComplaints').val(data.primaryComplaints);
        $('#primaryInspection').val(data.primaryInspection);
        drawOptions("#primaryDiagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.primaryDiagnosisId);
        drawOptions("#finalDiagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.finalDiagnosisId);
    });
    $('#editRow').modal();
}

function updateDischarge(id) {
    $('#modalTitle').html(i18n["editDischargeTitle"]);
    form.find(":input").val("");
    form.find('.disabled').removeClass('disabled');
    $('#primaryComplaints').attr("disabled", true);
    $('#primaryInspection').attr("disabled", true);
    $('#primaryDiagnosisId').attr("disabled", true);
    $('#dischargeDate').attr("disabled", false);
    $('#finalDiagnosisId').attr("disabled", false);
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#primaryComplaints').val(data.primaryComplaints);
        $('#primaryInspection').val(data.primaryInspection);
        drawOptions("#primaryDiagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.primaryDiagnosisId);
        drawOptions("#finalDiagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.finalDiagnosisId);
    });
    $('#editRow').modal();
}


function renderPrimaryExamBtn(data, type, row) {
    if (type === 'display') {
        return '<a onclick="updatePrimaryExam(' + row.id + ');">' + i18n["primaryExamBtn"] + '</a>';
    }
}

function renderDiagnosisBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='diagnosis?id=" + row.id
                                                                       + "&name=" + getFullName(row) + "'>"
                                                                       + i18n["diagnosisBtn"]
                                                                       + "</a>";
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
        return "<a class='btn btn-xs btn-primary' href='inspection?id=" + row.id
                                                                        + "&name=" + getFullName(row) + "'>"
                                                                        + i18n["inspectionBtn"]
                                                                        + "</a>";
    }
}

function renderDischargeBtn(data, type, row) {
    if (type === 'display') {
        return "<a onclick='updateDischarge(" + row.id + ");'>" + i18n["dischargeBtn"] + "</a>";
    }
}

function getFullName(row) {
    return row.surname + " " + row.name + " " + row.additionalName;

}