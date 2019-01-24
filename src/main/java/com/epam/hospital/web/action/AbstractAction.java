package com.epam.hospital.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAction implements Action{
    private String uri;

    public AbstractAction(String uri) {
        this.uri = uri;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);


}
