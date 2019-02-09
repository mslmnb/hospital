var ajaxUrl = 'patients/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl + "all", updateTableByData);
}


$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "surname"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderInfoBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderReceptionBtn
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


function renderInfoBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='patients/info?id=" + row.id + "'>"
                                                                           + i18n["infoBtn"]
                                                                           + "</a>";
    }
}

function renderReceptionBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='patients/reception?id=" + row.id + "'>"
                                                                                + i18n["receptionBtn"]
                                                                                + "</a>";
    }
}

function renderDiagnosisBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='patients/diagnosis?id=" + row.id + "'>"
                                                                                + i18n["diagnosisBtn"]
                                                                                + "</a>";
    }
}

function renderPrescriptionBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='patients/prescription?id=" + row.id + "'>"
                                                                                   + i18n["prescriptionBtn"]
                                                                                   + "</a>";
    }
}

function renderInspectionBtn(data, type, row) {
    if (type === 'display') {
        return "<a class='btn btn-xs btn-primary' href='patients/inspection?id=" + row.id + "'>"
                                                                                     + i18n["inspectionBtn"]
                                                                                     + "</a>";
    }
}
