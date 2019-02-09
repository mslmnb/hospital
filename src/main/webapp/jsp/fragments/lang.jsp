<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<c:set var="handbkParameter" value="${param.handbk}"/>
<c:set var="handbkItemIdParameter" value="${param.handbkItemId}"/>

<c:choose>
    <c:when test="${handbkParameter==null}">
        <c:set var="handbkParaPhrase" scope="request" value=""/>
    </c:when>
    <c:otherwise>
        <c:set var="handbkParaPhrase" scope="request" value="&handbk=${handbkParameter}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${handbkItemIdParameter==null}">
        <c:set var="handbkParahandbkItemIdPhrase" scope="request" value=""/>
    </c:when>
    <c:otherwise>
        <c:set var="handbkParahandbkItemIdPhrase" scope="request" value="&handbkItemId=${handbkItemIdParameter}"/>
    </c:otherwise>
</c:choose>


<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${lang}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en${handbkParaPhrase}${handbkParahandbkItemIdPhrase}" )%>English</a>
        </li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru${handbkParaPhrase}${handbkParahandbkItemIdPhrase}" )%>Русский</a>
        </li>

    </ul>
</li>
<script type="text/javascript">
    var localeCode = "${pageContext.response.locale}";
</script>