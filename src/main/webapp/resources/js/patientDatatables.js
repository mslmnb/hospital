var ajaxUrl = 'ajax/patients/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

// function enable(chkbox, id) {
//     var enabled = chkbox.is(":checked");
//     $.ajax({
//         url: ajaxUrl + id,
//         type: 'POST',
//         data: 'enabled=' + enabled,
//         success: function () {
//             chkbox.closest('tr').toggleClass('disabled');
//             successNoty(enabled ? 'Enabled' : 'Disabled');
//         },
//         error: function () {
//             $(chkbox).prop("checked", !enabled);
//         }
//     });
// }

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": true,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "surName"
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
        ],
        "initComplete": makeEditable
    });
});

function renderInfoBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-xs btn-primary" href="patients/info&id=' + row.id + '">' +
            'Info</a>';
    }
}

function renderReceptionBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-xs btn-primary" href="patients/reception&id=' + row.id + '">' +
            'Reception</a>';
    }
}

function renderDiagnosisBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-xs btn-primary" href="patients/diagnosis&id=' + row.id + '">' +
            'Diagnosis</a>';
    }
}

function renderPrescriptionBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-xs btn-primary" href="patients/prescription&id=' + row.id + '">' +
            'Prescription</a>';
    }
}

function renderInspectionBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="btn btn-xs btn-primary" href="patients/inspection&id=' + row.id + '">' +
            'Inspection</a>';
    }
}
