<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages/app"/>

<script type="text/javascript">
    var i18n=[];
    i18n["common.add"] = "<fmt:message key="common.add"/>";
    i18n["common.search"] = "<fmt:message key="common.search"/>";
    i18n["common.first"] = "<fmt:message key="common.first"/>";
    i18n["common.last"] = "<fmt:message key="common.last"/>";
    i18n["common.next"] = "<fmt:message key="common.next"/>";
    i18n["common.previous"] = "<fmt:message key="common.previous"/>";
    i18n["common.lengthMenu"] = "<fmt:message key="common.lengthMenu"/>";
</script>
