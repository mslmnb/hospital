var ajaxUrl = 'reception/';
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
                "data": "admissionDate"
            },
            {
                "data": "dischargeDate"
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

    $('#admissionDate').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    });

    $('#birthday').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

    });

