<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages/app"/>

<div class="footer">
    <div class="container">
        <fmt:message key="app.footer"/>
    </div>
</div>