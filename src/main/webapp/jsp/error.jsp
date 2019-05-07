<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<div class="jumbotron">
    <div class="container">
        <div class="col-sm-3">
        <img src="resources/error.png">
        </div>
        <div class="col-sm-9">
            <h3><fmt:message key="error.title"/></h3>
            <fmt:message key="error.request"/>: ${pageContext.errorData.requestURI} <br>
            <fmt:message key="error.statusCode"/>: ${pageContext.errorData.statusCode} <br>
            <c:if test="${pageContext.exception!=null}">
                <fmt:message key="error.exception"/>:  ${pageContext.exception} <br>
            </c:if>
            <fmt:message key="error.goto"/><a href=""> <fmt:message key="error.homePage"/></a>
        </div>
   </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>

</html>
