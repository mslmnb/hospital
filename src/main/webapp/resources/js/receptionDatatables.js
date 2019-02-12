var ajaxUrl = 'reception/';
var datatableApi;

// function updateTable() {
//     $.get(ajaxUrl + "all", updateTableByData);
// }


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
                "data": "admissionDate"
            },
            {
                "data": "dischargeDate"
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
    $.datetimepicker.setLocale(localeCode);

    $('#admissionDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

    $('#birthday').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

    });

