<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/inspectionDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="prescrptn.title"/>: ${param.name}</h3>
            <input type="hidden"
                   id="requestParameter"
                   name="requestParameter"
                   value="?id=${param.id}&name=${param.name}">


            <div class="view-box">
                <a class="btn btn-info" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="common.add"/>
                </a>
                <hr>
                <hr>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="inspection.date"/></th>
                        <th><fmt:message key="inspection.complaints"/></th>
                        <th><fmt:message key="inspection.inspection"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="modalTitle"></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input class="entry-field" type="hidden" id="id" name="id">
                    <input type="hidden" id="patientId" name="patientId" value="${param.id}">

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3">
                            <fmt:message key="inspection.date"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control entry-field"
                                   id="date" name="date"
                                   placeholder="<fmt:message key="inspection.date"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="complaints" class="control-label col-xs-3">
                            <fmt:message key="inspection.complaints"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control entry-field" id="complaints" name="complaints"
                                   placeholder="<fmt:message key="inspection.complaints"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="complaints" class="control-label col-xs-3">
                            <fmt:message key="inspection.inspection"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control entry-field" id="inspection" name="inspection"
                                   placeholder="<fmt:message key="inspection.inspection"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="button" onclick="save()" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>

<jsp:include page="fragments/i18n.jsp"/>
<script type="text/javascript">
    i18n["addTitle"] = "<fmt:message key="inspection.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="inspection.editTitle"/>"
    i18n["emptyDate"] = "<fmt:message key="error.inspection.emptyDate"/>"
    i18n["emptyComplaints"] = "<fmt:message key="error.inspection.emptyComplaints"/>"
    i18n["emptyInspection"] = "<fmt:message key="error.inspection.emptyInspection"/>"
    i18n["notFound"] = "<fmt:message key="error.inspection.notFound"/>"
</script>
