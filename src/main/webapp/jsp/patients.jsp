<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/patientDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="patients.title"/></h3>
            <hr>
            <hr>
            <div class="view-box">
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="patients.surname"/></th>
                        <th><fmt:message key="patients.name"/></th>
                        <th><fmt:message key="patients.additionalName"/></th>
                        <th></th>
                        <th></th>
                        <th></th>
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
                    <input type="hidden" class="input" id="id" name="id">

                    <div class="form-group">
                        <label for="primaryComplaints" class="control-label col-xs-3"><fmt:message key="patients.complaints"/></label>

                        <div class="col-xs-9">
                            <textarea rows="3"
                                      class="form-control input"
                                      id="primaryComplaints"
                                      name="primaryComplaints"
                                      placeholder="<fmt:message key="patients.complaints"/>">
                            </textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="primaryInspection" class="control-label col-xs-3"><fmt:message key="patients.inspection"/></label>

                        <div class="col-xs-9">
                            <textarea rows="3"
                                   class="form-control input"
                                   id="primaryInspection"
                                   name="primaryInspection"
                                   placeholder="<fmt:message key="patients.inspection"/>">
                            </textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="primaryDiagnosisId" class="control-label col-xs-3"><fmt:message key="patients.primaryDiagnosis"/></label>

                        <div class="col-xs-9">
                            <select class="form-control" id="primaryDiagnosisId" name="primaryDiagnosisId">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="dischargeDate" class="control-label col-xs-3"><fmt:message key="diagnosis.data"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="dischargeDate" name="dischargeDate"
                                   placeholder="<fmt:message key="patients.dischargeDate"/>">
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="finalDiagnosisId" class="control-label col-xs-3"><fmt:message key="patients.finalDiagnosis"/></label>

                        <div class="col-xs-9">
                            <select class="form-control" id="finalDiagnosisId" name="finalDiagnosisId">
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
    i18n["diagnosisBtn"] = "<fmt:message key="patients.diagnosis"/>";
    i18n["prescriptionBtn"] = "<fmt:message key="patients.prescription"/>";
    i18n["inspectionBtn"] = "<fmt:message key="patients.inspection"/>";
    i18n["primaryExamBtn"] = "<fmt:message key="patients.primaryExam"/>";
    i18n["dischargeBtn"] = "<fmt:message key="patients.discharge"/>";
    i18n["editPrimaryExamTitle"] = "<fmt:message key="patients.editPrimaryExamTitle"/>";
    i18n["selectDiagnosis"] = "<fmt:message key="patients.selectDiagnosis"/>";
    i18n["emptyFinalDiagnosisId"] = "<fmt:message key="error.patients.emptyFinalDiagnosisId"/>";
    i18n["emptyDischargeDate"] = "<fmt:message key="error.patients.emptyDischargeDate"/>";
    i18n["emptyDischargeDate"] = "<fmt:message key="error.patients.emptyDischargeDate"/>";
    i18n["emptyPrimaryComplaints"] = "<fmt:message key="error.patients.emptyPrimaryComplaints"/>";
    i18n["emptyPrimaryInspection"] = "<fmt:message key="error.patients.emptyPrimaryInspection"/>";
    i18n["emptyPrimaryDiagnosisId"] = "<fmt:message key="error.patients.emptyPrimaryDiagnosisId"/>";
    i18n["notFound"] = "<fmt:message key="error.patients.notFound"/>";
    i18n["editDischargeTitle"] ="<fmt:message key="patients.editDischargeTitle"/>";
</script>

</html>
