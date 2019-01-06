<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Patients</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h2>Patient list</h2>
    <hr>
    <a href="patients?action=create">Add patient</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
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
                <td><a href="patients?action=info&id=${patient.id}">Info</a></td>
                <td><a href="patients?action=reception&id=${patient.id}">Reception</a></td>
                <td><a href="patients?action=diagnosis&id=${patient.id}">Diagnosis</a></td>
                <td><a href="patients?action=prescription&id=${patient.id}">Prescription</a></td>
                <td><a href="patients?action=inspection&id=${patient.id}">Inspection</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
