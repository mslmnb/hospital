var ajaxUrl = 'diagnosis/';
var datatableApi;

function add() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(".entry-field").val("");
    drawOptions("#diagnosisTypeId", "handbk/translation?type=diagnosis_type", "selectDiagnosisType");
    drawOptions("#diagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis");
    $('#editRow').modal();
}

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    form.find(".entry-field").val("");
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        drawOptions("#diagnosisTypeId", "handbk/translation?type=diagnosis_type",
            "selectDiagnosisType", data.diagnosisTypeId);
        drawOptions("#diagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis", data.diagnosisId);
    });
    $('#editRow').modal();
}

$(function () {
    datatableApi = $('#datatable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "date"
            },
            {
                "data": "diagnosisType"
            },
            {
                "data": "diagnosis"
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

    var date = $('#date');
    date.datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

});






