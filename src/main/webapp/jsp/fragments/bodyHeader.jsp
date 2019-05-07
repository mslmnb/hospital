<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a class="navbar-brand"><fmt:message key="app.title"/></a>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form navbar-right" action="logout" method="post">
                        <button class="btn btn-primary" type="submit">
                            ${sessionScope.authorizedUser}
                            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                        </button>
                        <input type="hidden" id="authorizedUserRole" value="${sessionScope.authorizedUser.role}" />
                    </form>
                </li>
                <jsp:include page="lang.jsp"/>
            </ul>
        </div>
    </div>
</div>