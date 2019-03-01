<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/receptionDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">

            <div class="row">
                <div class="col-sm-11">
                    <h3><fmt:message key="reception.title"/></h3>
                </div>
                <br>
                <div class="col-sm-1">
                    <a href="noAdmin"><fmt:message key="common.back"/></a>
                </div>
            </div>

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
                        <th><fmt:message key="patients.surname"/></th>
                        <th><fmt:message key="patients.name"/></th>
                        <th><fmt:message key="patients.additionalName"/></th>
                        <th><fmt:message key="reception.admissionDate"/></th>
                        <th><fmt:message key="reception.dischargeDate"/></th>
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
                        <label for="name" class="control-label col-xs-3"><fmt:message key="patients.name"/></label>

                        <div class="col-xs-9">
                            <input type="text"
                                   class="form-control input"
                                   id="name" name="name"
                                   placeholder="<fmt:message key="patients.name"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="additionalName" class="control-label col-xs-3">
                            <fmt:message key="patients.additionalName"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text"
                                   class="form-control input"
                                   id="additionalName"
                                   name="additionalName"
                                   placeholder="<fmt:message key="patients.additionalName"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="control-label col-xs-3">
                            <fmt:message key="patients.surname"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text"
                                   class="form-control input"
                                   id="surname"
                                   name="surname"
                                   placeholder="<fmt:message key="patients.surname"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="birthday" class="control-label col-xs-3">
                            <fmt:message key="reception.birthday"/>
                        </label>

                        <div class="col-xs-9">
                            <input class="form-control input"
                                   id="birthday"
                                   name="birthday"
                                   placeholder="<fmt:message key="reception.birthday"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="control-label col-xs-3"><fmt:message key="reception.phone"/></label>

                        <div class="col-xs-9">
                            <input type="text"
                                   class="form-control input"
                                   id="phone" name="phone"
                                   placeholder="<fmt:message key="reception.phone"/>"/>
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="email" class="control-label col-xs-3"><fmt:message key="reception.email"/></label>

                        <div class="col-xs-9">
                            <input type="email"
                                   class="form-control input"
                                   id="email" name="email"
                                   placeholder="<fmt:message key="reception.email"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="birthday" class="control-label col-xs-3">
                            <fmt:message key="reception.admissionDate"/>
                        </label>

                        <div class="col-xs-9">
                            <input class="form-control input"
                                   id="admissionDate"
                                   name="admissionDate"
                                   placeholder="<fmt:message key="reception.admissionDate"/>">
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
    i18n["addTitle"] = "<fmt:message key="reception.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="reception.editTitle"/>"
    i18n["invalidEmail"] = "<fmt:message key="error.reception.invalidEmail"/>";
    i18n["invalidPhone"] = "<fmt:message key="error.reception.invalidPhone"/>";
    i18n["emptyName"] = "<fmt:message key="error.reception.emptyName"/>";
    i18n["emptySurname"] = "<fmt:message key="error.reception.emptySurname"/>";
    i18n["emptyBirthday"] = "<fmt:message key="error.reception.emptyBirthday"/>";
    i18n["invalidBirthday"] = "<fmt:message key="error.reception.invalidBirthday"/>";
    i18n["emptyAdmissionDate"] = "<fmt:message key="error.reception.emptyAdmissionDate"/>";
    i18n["invalidAdmissionDate"] = "<fmt:message key="error.reception.invalidAdmissionDate"/>";
    i18n["notFound"] = "<fmt:message key="error.reception.notFound"/>";
    i18n["impossibleRemovingForDiagnosises"] = "<fmt:message key="error.reception.impossibleRemovingForDiagnosises"/>"
    i18n["impossibleRemovingForInspections"] = "<fmt:message key="error.reception.impossibleRemovingForInspections"/>"
    i18n["impossibleRemovingForPrescriptions"] = "<fmt:message key="error.reception.impossibleRemovingForPrescriptions"/>"

</script>

</html>
