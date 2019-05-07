package com.epam.hospital.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The root interface in the action hierarchy.
 * The action describes what needs to be made when receiving a specified request.
 */

public interface Action {

    /**
     * Performs necessary operations and returns a view name
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @param response the {@code HttpServletResponse} object contains the filter's response
     * @return the view name
     */
    String execute(HttpServletRequest request, HttpServletResponse response);

    /**
     * Returns the request string.
     * @return the request string.
     */
    String getUri();

}

