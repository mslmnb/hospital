<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="js/patientDatatables.js" defer></script>

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
                <c:forEach items="${patients}" var="patient">
                    <jsp:useBean id="patient" scope="page" type="com.epam.hospital.to.PatientTo"/>
                    <tr class="normal">
                        <td>${patient.name}</td>
                        <td>${patient.surName}</td>
                        <td><a class="btn btn-xs btn-primary" href="patients?action=info&id=${patient.id}">Info</a></td>
                        <td><a class="btn btn-xs btn-primary" href="patients?action=reception&id=${patient.id}">Reception</a></td>
                        <td><a class="btn btn-xs btn-primary" href="patients?action=diagnosis&id=${patient.id}">Diagnosis</a></td>
                        <td><a class="btn btn-xs btn-primary" href="patients?action=prescription&id=${patient.id}">Prescription</a></td>
                        <td><a class="btn btn-xs btn-primary" href="patients?action=inspection&id=${patient.id}">Inspection</a></td>
                    </tr>
                </c:forEach>
            </table>
            </div>
        </div>
    </div>
    <jsp:include page="fragments/footer.jsp"/>

    <%-- здесь добавить скрытое окно добавления/редактирования пациента--%>

</div>
</body>
</html>
