<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/handbkDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="handbk.${param.type}.title"/></h3>
            <input type="hidden"
                   id="requestParameter"
                   name="requestParameter"
                   value="?type=${param.type}">

            <div class="view-box">

                <a class="btn btn-info" onclick="addHandbk()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="common.add"/>
                </a>
                <hr>
                <hr>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="handbk.name"/></th>
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
                    <input type="hidden"
                           id="type"
                           name="type"
                           value="${param.type}">

                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><fmt:message key="handbk.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="<fmt:message key="handbk.name"/>">
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
    i18n["translation"] = "<fmt:message key="handbk.translation"/>"
    i18n["addTitle"] = "<fmt:message key="handbk.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="handbk.editTitle"/>"
    i18n["emptyName"] = "<fmt:message key="error.handbk.emptyName"/>"
    i18n["impossibleRemovingForStaff"] = "<fmt:message key="error.handbk.impossibleRemovingForStaff"/>"
    i18n["impossibleRemovingForDiagnosis"] = "<fmt:message key="error.handbk.impossibleRemovingForDiagnosis"/>"
</script>

</html>

