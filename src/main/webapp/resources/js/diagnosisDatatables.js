var ajaxUrl = 'diagnosis/';
var datatableApi;

function add() {
    $('#modalTitle').html(i18n["addTitle"]);
    form.find(".input").val("");
    drawOptions("#diagnosisTypeId", "handbk/translation?type=diagnosis_type", "selectDiagnosisType");
    drawOptions("#diagnosisId", "handbk/translation?type=diagnosis", "selectDiagnosis");
    $('#editRow').modal();
}

function updateRow(id) {
    $('#modalTitle').html(i18n["editTitle"]);
    form.find(".input").val("");
    $.get(ajaxUrl + "get?id=" + id, function (data) {
        $.each(data, function (key, value) {
            form.find(".input[name='" + key + "']").val(value);
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

    $('#date').datetimepicker({
        timepicker: false,
        format: 'd-m-Y',
        formatDate: 'd-m-Y'
    })

});






