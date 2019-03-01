<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>

<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/translationDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <div class="row">
                <div class="col-sm-11">
                    <h3><fmt:message key="translation.title"/> ${param.name}</h3>
                </div>
                <br>
                <div class="col-sm-1">
                    <a href="handbk?type=${param.type}"><fmt:message key="common.back"/></a>
                </div>
            </div>
            <input type="hidden"
                   id="requestParameter"
                   name="requestParameter"
                   value="?type=${param.type}&id=${param.id}&name=${param.name}">

            <div class="view-box">

                <a class="btn btn-info" onclick="addTranslation()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="common.add"/>
                </a>
                <hr>
                <hr>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="translation.locale"/></th>
                        <th><fmt:message key="translation.translation"/></th>
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
                           id="handbkItemId"
                           name="handbkItemId"
                           value="${param.id}">

                    <input class="input" type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="locale" class="control-label col-xs-3">
                            <fmt:message key="translation.locale"/>
                        </label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="locale" name="locale">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="translation" class="control-label col-xs-3">
                            <fmt:message key="translation.translation"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="translation" name="translation"
                                   placeholder="<fmt:message key="translation.translation"/>">
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
    i18n["addTitle"] = "<fmt:message key="translation.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="translation.editTitle"/>"
    i18n["selectLocale"] = "<fmt:message key="translation.selectLocale"/>"
    i18n["emptyTranslation"] = "<fmt:message key="error.translation.emptyTranslation"/>"
    i18n["emptyLocale"] = "<fmt:message key="error.translation.emptyLocale"/>"
    i18n["notFound"] = "<fmt:message key="error.translation.notFound"/>"
    i18n["notUniqueItemAndLocale"] = "<fmt:message key="error.translation.notUniqueItemAndLocale"/>"
</script>

</html>
