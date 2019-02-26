<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>

<jsp:include page="fragments/headTag.jsp"/>

<body>

<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/prescrptnDatatables.js" defer></script>

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
                <a class="btn btn-info" id="addButton" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="common.add"/>
                </a>
                <hr>
                <hr>
                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="prescrptn.applicationDate"/></th>
                        <th><fmt:message key="prescrptn.type"/></th>
                        <th><fmt:message key="prescrptn.description"/></th>
                        <th><fmt:message key="prescrptn.executionDate"/></th>
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
                    <input class="input" type="hidden" id="id" name="id">
                    <input type="hidden" id="patientId" name="patientId" value="${param.id}">

                    <div class="form-group">
                        <label for="applicationDate" class="control-label col-xs-3">
                            <fmt:message key="prescrptn.applicationDate"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input"
                                   id="applicationDate" name="applicationDate"
                                   placeholder="<fmt:message key="prescrptn.applicationDate"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="typeId" class="control-label col-xs-3">
                            <fmt:message key="prescrptn.type"/>
                        </label>

                        <div class="col-xs-9">
                            <select class="form-control input" id="typeId" name="typeId">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">
                            <fmt:message key="prescrptn.description"/>
                        </label>

                        <div class="col-xs-9">
                            <textarea rows="3"
                                    class="form-control input"
                                    id="description"
                                    name="description"
                                    placeholder="<fmt:message key="prescrptn.description"/>">
                            </textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="applicationDate" class="control-label col-xs-3">
                            <fmt:message key="prescrptn.executionDate"/>
                        </label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control input" id="executionDate" name="executionDate"
                                   placeholder="<fmt:message key="prescrptn.executionDate"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="result" class="control-label col-xs-3">
                            <fmt:message key="prescrptn.result"/>
                        </label>

                        <div class="col-xs-9">
                            <textarea rows="3"
                                      class="form-control input"
                                      id="result"
                                      name="result"
                                      placeholder="<fmt:message key="prescrptn.result"/>">
                            </textarea>
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
    i18n["addTitle"] = "<fmt:message key="prescrptn.addTitle"/>"
    i18n["editTitle"] = "<fmt:message key="prescrptn.editTitle"/>"
    i18n["selectPrescrptnType"] = "<fmt:message key="prescrptn.selectPrescrptnType"/>"
    i18n["emptyApplicationDate"] = "<fmt:message key="error.prescrptn.emptyApplicationDate"/>"
    i18n["emptyExecutionDate"] = "<fmt:message key="error.prescrptn.emptyExecutionDate"/>"
    i18n["emptyDescription"] = "<fmt:message key="error.prescrptn.emptyDescription"/>"
    i18n["emptyTypeId"] = "<fmt:message key="error.prescrptn.emptyTypeId"/>"
    i18n["emptyResult"] = "<fmt:message key="error.prescrptn.emptyResult"/>"
    i18n["notFound"] = "<fmt:message key="error.prescrptn.notFound"/>"
    i18n["execution"] = "<fmt:message key="prescrptn.execution"/>"
    i18n["executionEditTitle"] = "<fmt:message key="prescrptn.executionEditTitle"/>"
</script>
