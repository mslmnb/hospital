<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/initReqParameter.tld" prefix="t" %>

<t:initReqParameter></t:initReqParameter>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${lang}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en${reqParameter}">English</a></li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru${reqParameter}">Русский</a></li>

    </ul>
</li>
<script type="text/javascript">
    var localeCode = "${pageContext.response.locale}";
</script>