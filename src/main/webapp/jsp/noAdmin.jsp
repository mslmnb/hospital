<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<script type="text/javascript" src="resources/js/noAdmin.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <br>
        <br>
        <div class="shadow">
            <a class="btn btn-info col-xs-4" id="receptionButton" href="reception">
                <fmt:message key="noAdmin.reception"/>
            </a>
            <br>
            <br>
            <a class="btn btn-info col-xs-4 role_doctor role_nurse" href="patients"><fmt:message key="noAdmin.patients"/></a>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>

</html>

