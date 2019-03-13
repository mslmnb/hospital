<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/userDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="row">
                <div class="col-sm-11">
                    <h3><fmt:message key="users.title"/></h3>
                </div>
                <br>
                <div class="col-sm-1">
                    <a href="admin"><fmt:message key="common.back"/></a>
                </div>
            </div>

            <div class="view-box">

                <a class="btn btn-info" onclick="addUser()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="common.add"/>
                </a>
                <hr>
                <hr>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="users.staff"/></th>
                        <th><fmt:message key="users.login"/></th>
                        <th><fmt:message key="users.role"/></th>
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
                        <label for="staffId" class="control-label col-xs-3"><fmt:message key="users.staff"/></label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="staffId" name="staffId">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="login" class="control-label col-xs-3"><fmt:message key="users.login"/></label>

                        <div class="col-xs-9">
                            <input type="text"
                                   class="form-control input"
                                   id="login"
                                   name="login"
                                   placeholder="<fmt:message key="users.login"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="role" class="control-label col-xs-3"><fmt:message key="users.role"/></label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="role" name="role">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="control-label col-xs-3"><fmt:message key="users.password"/></label>

                        <div class="col-xs-9">
                            <input type="text"
                                   class="form-control input"
                                   id="password"
                                   name="password"
                                   placeholder="<fmt:message key="users.password"/>"/>
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
    i18n["selectStaff"] = "<fmt:message key="users.selectStaff"/>"
    i18n["selectRole"] = "<fmt:message key="users.selectRole"/>"
    i18n["emptyId"] = "<fmt:message key="error.users.emptyId"/>"
    i18n["emptyStaffId"] = "<fmt:message key="error.users.emptyStaffId"/>"
    i18n["emptyLogin"] = "<fmt:message key="error.users.emptyLogin"/>"
    i18n["emptyRole"] = "<fmt:message key="error.users.emptyRole"/>"
    i18n["emptyPassword"] = "<fmt:message key="error.users.emptyPassword"/>"
    i18n["notUniqueLogin"] = "<fmt:message key="error.users.notUniqueLogin"/>"
    i18n["notUniqueStaffAndRole"] = "<fmt:message key="error.users.notUniqueStaffAndRole"/>"
    i18n["notFound"] = "<fmt:message key="error.users.notFound"/>"
    i18n["addTitle"] = "<fmt:message key="users.addTitle"/>"
    i18n["editPassword"] = "<fmt:message key="users.editPassword"/>"
    i18n["invalidId"] = "<fmt:message key="error.users.invalidId"/>"
    i18n["modificationRestriction"] = "<fmt:message key="error.users.modificationRestriction"/>"
</script>

</html>
