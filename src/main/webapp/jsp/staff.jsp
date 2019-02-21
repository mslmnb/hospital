<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/staffDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="staff.title"/></h3>

            <div class="view-box">

                <a class="btn btn-info" onclick="addStaff()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="common.add"/>
                </a>
                <hr>
                <hr>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="staff.name"/></th>
                        <th><fmt:message key="staff.additionalName"/></th>
                        <th><fmt:message key="staff.surname"/></th>
                        <th><fmt:message key="staff.position"/></th>
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
                        <label for="name" class="control-label col-xs-3"><fmt:message key="staff.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="name" name="name"
                                   placeholder="<fmt:message key="staff.name"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="additionalName" class="control-label col-xs-3">
                            <fmt:message key="staff.additionalName"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="additionalName" name="additionalName"
                                   placeholder="<fmt:message key="staff.additionalName"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="surname" class="control-label col-xs-3"><fmt:message key="staff.surname"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="surname" name="surname"
                                   placeholder="<fmt:message key="staff.surname"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="positionId" class="control-label col-xs-3">
                            <fmt:message key="staff.position"/>
                        </label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="positionId" name="positionId">
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
    i18n["addTitle"] = "<fmt:message key="staff.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="staff.editTitle"/>"
    i18n["selectPosition"] = "<fmt:message key="staff.selectPosition"/>"
    i18n["emptyName"] = "<fmt:message key="error.staff.emptyName"/>"
    i18n["emptySurname"] = "<fmt:message key="error.staff.emptySurname"/>"
    i18n["emptyPositionId"] = "<fmt:message key="error.staff.emptyPositionId"/>"
    i18n["impossibleRemovingForUsers"] = "<fmt:message key="error.staff.impossibleRemovingForUsers"/>"
    i18n["notFound"] = "<fmt:message key="error.staff.notFound"/>"
</script>

</html>
