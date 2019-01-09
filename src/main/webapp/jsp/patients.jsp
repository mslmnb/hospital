<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/patientDatatables.js" defer></script>

<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <%--<h3><spring:message code="patients.title"/></h3>--%>
            <h3>Patient list</h3>

            <div class="view-box">

                <a class="btn btn-info" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </a>
                <hr>
            <hr>
            <table class="table table-striped display" id="datatable">
                <thead>
                <tr>
                    <%--<th><spring:message code="meals.dateTime"/></th>--%>
                    <%--<th><spring:message code="meals.description"/></th>--%>
                    <th>Name</th>
                    <th>Surname</th>
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
                <%--<h2 class="modal-title"><spring:message code="users.add"/></h2>--%>
                <h2 class="modal-title">Добавить пациента</h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <%--<label for="name" class="control-label col-xs-3"><spring:message code="users.name"/></label>--%>
                        <label for="name" class="control-label col-xs-3">Имя</label>

                        <div class="col-xs-9">
                            <%--<input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="users.name"/>">--%>
                            <input type="text" class="form-control" id="name" name="name" placeholder="имя"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <%--<label for="name" class="control-label col-xs-3"><spring:message code="users.name"/></label>--%>
                        <label for="additional_name" class="control-label col-xs-3">Отчество</label>

                        <div class="col-xs-9">
                            <%--<input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="users.name"/>">--%>
                            <input type="text" class="form-control" id="additional_name" name="additional_name" placeholder="отчество"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <%--<label for="name" class="control-label col-xs-3"><spring:message code="users.name"/></label>--%>
                        <label for="surname" class="control-label col-xs-3">Фамилия</label>

                        <div class="col-xs-9">
                            <%--<input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="users.name"/>">--%>
                            <input type="text" class="form-control" id="surname" name="surname" placeholder="фамилия"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <%--<label for="dateTime" class="control-label col-xs-3"><spring:message code="meals.dateTime"/></label>--%>
                        <label for="birthday" class="control-label col-xs-3">Дата рождения</label>

                        <div class="col-xs-9">
                            <%--<input type="datetime-local" class="form-control" id="dateTime" name="dateTime" placeholder="<spring:message code="meals.dateTime"/>">--%>
                            <input type="datetime-local" class="form-control" id="birthday" name="birthday" placeholder="дата рождения">
                        </div>
                    </div>

                    <div class="form-group">
                        <%--<label for="name" class="control-label col-xs-3"><spring:message code="users.name"/></label>--%>
                        <label for="phone" class="control-label col-xs-3">Телефон</label>

                        <div class="col-xs-9">
                            <%--<input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="users.name"/>">--%>
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="фамилия"/>
                        </div>
                    </div>


                    <div class="form-group">
                        <%--<label for="email" class="control-label col-xs-3"><spring:message code="users.email"/></label>--%>
                        <label for="email" class="control-label col-xs-3">Адрес email</label>

                        <div class="col-xs-9">
                            <%--<input type="email" class="form-control" id="email" name="email" placeholder="<spring:message code="users.email"/>">--%>
                            <input type="email" class="form-control" id="email" name="email" placeholder="адрес email"/>
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

</body>
</html>
