<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="app.title"/></title>
    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="resources/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="resources/webjars/datatables/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="resources/webjars/datatables/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="resources/webjars/datetimepicker/jquery.datetimepicker.css">
    <link rel="shortcut icon" href="resources/hospital.png">

    <script type="text/javascript" src="resources/webjars/jquery/jquery.min.js" defer></script>
    <script type="text/javascript" src="resources/webjars/bootstrap/js/bootstrap.min.js" defer></script>
    <script type="text/javascript" src="resources/webjars/datatables/js/jquery.dataTables.min.js" defer></script>
    <script type="text/javascript" src="resources/webjars/datatables/js/dataTables.bootstrap.min.js" defer></script>
    <script type="text/javascript" src="resources/webjars/datetimepicker/build/jquery.datetimepicker.full.min.js" defer></script>
    <script type="text/javascript" src="resources/webjars/noty/jquery.noty.packaged.min.js" defer></script>

</head>