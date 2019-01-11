package com.epam.hospital.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAction implements Action{
    private String attributeName;
    private String uri;
    private String jspFile;

    public AbstractAction(String attributeName, String uri, String jspFile) {
        this.attributeName = attributeName;
        this.uri = uri;
        this.jspFile = jspFile;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public String getUri() {
        return uri;
    }

    public String getJspFile() {
        return jspFile;
    }

    @Override
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
