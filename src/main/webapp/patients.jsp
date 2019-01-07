<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="webjars/bootstrap/css/bootstrap.min.css">
    <%--<link rel="stylesheet" href="webjars/datatables/1.10.15/media/css/jquery.dataTables.min.css">--%>
    <%--<link rel="stylesheet" href="webjars/datatables/1.10.15/media/css/dataTables.bootstrap.min.css">--%>
    <title>Patients</title>
    <script type="text/javascript" src="webjars/jquery/dist/jquery.min.js" defer></script>
    <%--<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>--%>
    <%--<script type="text/javascript" src="webjars/datatables/1.10.15/media/js/jquery.dataTables.min.js" defer></script>--%>
    <%--<script type="text/javascript" src="webjars/datatables/1.10.15/media/js/dataTables.bootstrap.min.js" defer></script>--%>
    <%--<script type="text/javascript" src="webjars/noty/2.4.1/js/noty/packaged/jquery.noty.packaged.min.js" defer></script>--%>

</head>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h2>Patient list</h2>
            <div class="view-box">
                <a class="btn btn-info">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </a>
                <a href="patients?action=create">Add patient</a>
                <hr>
            <hr>
            <table class="table table-striped display" border="1" cellpadding="8" cellspacing="0">
                <thead>
                <tr>
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
</div>
</body>
</html>
