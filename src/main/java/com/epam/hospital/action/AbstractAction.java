package com.epam.hospital.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides a skeletal implementation of the <tt>Action</tt> interface,
 * to minimize the effort required to implement this interface.
 */

public abstract class AbstractAction implements Action{

    /**
     * the request string
     */
    private final String uri;

    /**
     * Constructs <tt>AbstractAction</tt> with the specified request string
     * @param uri the request string
     */
    AbstractAction(String uri) {
        this.uri = uri;
    }

    /**
     * Returns the request string.
     * @return the request string.
     */
    @Override
    public String getUri() {
        return uri;
    }

    /**
     * Performs necessary operations and returns a view name
     * @param request the http request
     * @param response the http response
     * @return the view name
     */
    @Override
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);

}
