package com.epam.hospital.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ActionUtil.TEXT_MIMETYPE;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class ErrorHandlerAction extends AbstractAction {
    public static final String URI = "error";

    public ErrorHandlerAction() {
        super(URI);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // logger

        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        response.setContentType(TEXT_MIMETYPE);
        StringBuilder content = new StringBuilder("<html><head><title>Exception/Error Details</title></head><body>");
        if(statusCode != 500) {
            content.append("<h3>Error Details</h3>");
            content.append("<strong>Status Code</strong>:" + statusCode + "<br>");
            content.append("<strong>Requested URI</strong>:"+requestUri);
        }else{
            content.append("<h3>Exception Details</h3>");
            content.append("<ul><li>Servlet Name:"+servletName+"</li>");
            content.append("<li>Exception Name:"+throwable.getClass().getName()+"</li>");
            content.append("<li>Requested URI:"+requestUri+"</li>");
            content.append("<li>Exception Message:"+throwable.getMessage()+"</li>");
            content.append("</ul>");
        }
        content.append("<br><br>");
        content.append("<a href=\"/hospital\">Home Page</a>");
        content.append("</body></html>");

        return JSON_VIEW_PREFIX.getPrefix() + content.toString();
    }
}
