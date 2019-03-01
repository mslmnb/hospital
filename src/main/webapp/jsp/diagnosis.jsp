<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/diagnosisDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="row">
                <div class="col-sm-11">
                    <h3><fmt:message key="diagnosis.title"/>: ${param.name}</h3>
                </div>
                <br>
                <div class="col-sm-1">
                    <a href="patients"><fmt:message key="common.back"/></a>
                </div>
            </div>

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
                        <th><fmt:message key="diagnosis.data"/></th>
                        <th><fmt:message key="diagnosis.type"/></th>
                        <th><fmt:message key="diagnosis.diagnosis"/></th>
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
                    <input class="input" type="hidden" id="id" name="id">
                    <input type="hidden" id="patientId" name="patientId" value="${param.id}">

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3"><fmt:message key="diagnosis.data"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="date" name="date"
                                   placeholder="<fmt:message key="diagnosis.data"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="diagnosisTypeId" class="control-label col-xs-3">
                            <fmt:message key="diagnosis.type"/>
                        </label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="diagnosisTypeId" name="diagnosisTypeId">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="diagnosisId" class="control-label col-xs-3">
                            <fmt:message key="diagnosis.diagnosis"/>
                        </label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="diagnosisId" name="diagnosisId">
                            </select>
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
    i18n["addTitle"] = "<fmt:message key="diagnosis.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="diagnosis.editTitle"/>"
    i18n["selectDiagnosisType"] = "<fmt:message key="diagnosis.selectDiagnosisType"/>"
    i18n["selectDiagnosis"] = "<fmt:message key="diagnosis.selectDiagnosis"/>"
    i18n["emptyDate"] = "<fmt:message key="error.diagnosis.emptyDate"/>"
    i18n["emptyDiagnosisTypeId"] = "<fmt:message key="error.diagnosis.emptyDiagnosisTypeId"/>"
    i18n["emptyDiagnosisId"] = "<fmt:message key="error.diagnosis.emptyDiagnosisId"/>"
    i18n["emptyPatientId"] = "<fmt:message key="error.diagnosis.emptyPatientId"/>"
    i18n["notFound"] = "<fmt:message key="error.diagnosis.notFound"/>"
</script>
