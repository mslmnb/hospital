<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>


<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <br>
        <br>
        <div class="shadow">
            <a class="btn btn-info col-xs-4" href="users"><fmt:message key="admin.users"/></a>
            <br>
            <br>
            <a class="btn btn-info col-xs-4" href="staff"><fmt:message key="admin.staff"/></a>
            <br>
            <br>
            <a class="btn btn-info col-xs-4" href="handbk?handbk=position"><fmt:message key="admin.positionHandbk"/></a>
            <br>
            <br>
            <a class="btn btn-info col-xs-4" href="handbk?handbk=diagnosis"><fmt:message key="admin.diagnosisHandbk"/></a>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>

<jsp:include page="fragments/i18n.jsp"/>
<script type="text/javascript">
    i18n["infoBtn"] = "<fmt:message key="patients.info"/>";
</script>

</html>
