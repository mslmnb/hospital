<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages/app"/>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="" class="navbar-brand"><fmt:message key="app.title"/></a>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <%--<form:form class="navbar-form navbar-right" action="logout" method="post">--%>
                        <%--<sec:authorize access="isAuthenticated()">--%>
                            <%--<a class="btn btn-info" href="profile">${userTo.name} <spring:message code="app.profile"/></a>--%>
                            <%--<button class="btn btn-primary" type="submit">--%>
                                <%--<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>--%>
                            <%--</button>--%>
                        <%--</sec:authorize>--%>
                    <%--</form:form>--%>
                </li>
                <jsp:include page="lang.jsp"/>
            </ul>
        </div>
    </div>
</div>