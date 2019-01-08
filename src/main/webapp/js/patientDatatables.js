var ajaxUrl = 'ajax/patients/';
var datatableApi;

$(function() {
    datatableApi = $('#datatable').DataTable({
        "paging"  : true,
        "info"    : true,
        "columns" : [
            {
                "data" :  "name"
            },
            {
                "data" : "surName"
            },
            {
                "defaultContent" : "Edit",
                "orderable"      : false
            },
            {
                "defaultContent" : "Edit",
                "orderable"      : false
            },
            {
                "defaultContent" : "Edit",
                "orderable"      : false
            },
            {
                "defaultContent" : "Edit",
                "orderable"      : false
            },
            {
                "defaultContent" : "Delete",
                "orderable"      : false
            }
        ],
        "order" : [
            [
                0, "asc"
            ]
        ]
    });
    makeEditable();
});
