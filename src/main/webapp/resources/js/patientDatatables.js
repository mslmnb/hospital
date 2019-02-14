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
            }
        ],
        "order": [
            [
                0, "asc"
            ]
        ]
    }));
    $.datetimepicker.setLocale(localeCode);

    var birthday = $('#birthday');
    birthday.datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })
});


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

function getFullName(row) {
    return row.surname + " " + row.name + " " + row.additionalName;

}