<%@page contentType="text/html" pageEncoding="UTF-8" %>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${lang}<b class="caret"></b></a>
    <ul class="dropdown-menu">
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a></li>
        <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a></li>

    </ul>
</li>
<script type="text/javascript">
    var localeCode = "${pageContext.response.locale}";
</script>