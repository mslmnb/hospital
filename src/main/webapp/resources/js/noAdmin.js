$(function () {
    if ($('#authorizedUserRole').val() == "ROLE_DOCTOR") {
        $("#receptionButton").addClass("disabled");
        $("#receptionButton").attr("href", "");
    }
});