package com.epam.hospital.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides a skeletal implementation of the {@code Action} interface,
 * to minimize the effort required to implement this interface.
 */

public abstract class AbstractAction implements Action{

    /**
     * the request string
     */
    private final String uri;

    /**
     * Constructs {@code AbstractAction} with the specified request string
     * @param uri the request string
     */
    AbstractAction(String uri) {
        this.uri = uri;
    }

    @Override
    public String getUri() {
        return uri;
    }

    /**
     * Performs necessary operations and returns a view name
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @param response the {@code HttpServletResponse} object contains the filter's response
     * @return the view name
     */
    @Override
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);

}
