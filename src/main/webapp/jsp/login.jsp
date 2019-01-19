<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header navbar-brand"><fmt:message key="app.title"/></div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form" role="form" action="login" method="post">
                        <div class="form-group">
                            <input type="text" placeholder="Login" class="form-control" name="login">
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control" name="password">
                        </div>
                        <button type="submit" class="btn btn-success">
                            <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                        </button>
                    </form>
                </li>
                <jsp:include page="fragments/lang.jsp"/>
            </ul>
        </div>
    </div>
</div>

<div class="jumbotron">
    <div class="container">
        <c:if test="${param.error}">
            <div class="error">
                <fmt:message key="app.login.err"/>
            </div>
        </c:if>
        <br/>
        <p>
            <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('Vigel_VA', 'password1')">
                <fmt:message key="app.enter"/> Admin
            </button>
            <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('Vigriyanov_KE', 'password2')">
                <fmt:message key="app.enter"/> Doctor
            </button>
            <button type="submit" class="btn btn-lg btn-primary" onclick="setCredentials('Vilisova_NE', 'password5')">
                <fmt:message key="app.enter"/> Nurse
            </button>
        </p>
        <br/>
    </div>
</div>
<div class="container">
    <div class="lead">
        &nbsp;&nbsp;&nbsp;<a href="https://github.com/mslmnb/hospital">Java Servlet Project</a> с
        авторизацией и интерфейсом на основе ролей (ADMIN, DOCTOR, NURSE).
        Администратор регистрирует штатный персонал клиники, дает им доступ в систему, редактирует справочную
        информацию.
        Доктор делает лечебные назначения(лечение/лекарства/процедуры) пациенту, выполняет назначения, проводит осмотры
        пациента.
        Медсестра регистрирует пациентов, выполняет лечебные назначения.
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    <c:if test="${not empty param.login}">
    setCredentials("${param.login}", "");
    </c:if>
    function setCredentials(login, password) {
        $('input[name="login"]').val(login);
        $('input[name="password"]').val(password);
    }
</script>
</body>
</html>