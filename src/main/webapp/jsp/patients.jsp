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
                    <th><fmt:message key="patients.name"/></th>
                    <th><fmt:message key="patients.surname"/></th>
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
                <h2 class="modal-title"><fmt:message key="patients.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><fmt:message key="patients.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="patients.name"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="additional_name" class="control-label col-xs-3"><fmt:message key="patients.additionalName"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="additional_name" name="additional_name" placeholder="<fmt:message key="patients.additionalName"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="control-label col-xs-3"><fmt:message key="patients.surname"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="surname" name="surname" placeholder="<fmt:message key="patients.surname"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="birthday" class="control-label col-xs-3"><fmt:message key="patients.birthday"/></label>

                        <div class="col-xs-9">
                            <input class="form-control" id="birthday" name="birthday" placeholder="<fmt:message key="patients.birthday"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="control-label col-xs-3"><fmt:message key="patients.phone"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="<fmt:message key="patients.phone"/>"/>
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="email" class="control-label col-xs-3"><fmt:message key="patients.email"/></label>

                        <div class="col-xs-9">
                            <input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="patients.email"/>"/>
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
    i18n["infoBtn"] = "<fmt:message key="patients.info"/>";
    i18n["receptionBtn"] = "<fmt:message key="patients.reception"/>";
    i18n["diagnosisBtn"] = "<fmt:message key="patients.diagnosis"/>";
    i18n["prescriptionBtn"] = "<fmt:message key="patients.prescription"/>";
    i18n["inspectionBtn"] = "<fmt:message key="patients.inspection"/>";
</script>

</html>
