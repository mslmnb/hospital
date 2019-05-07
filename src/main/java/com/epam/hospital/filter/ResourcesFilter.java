package com.epam.hospital.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The filter redirects requests to front controller servlet
 * except requests to resources {@code RESOURCES_PATH} and {@code ROOT_PATH}
 */

public class ResourcesFilter implements Filter {
    private static final String SERVLET_PATH = "/app";
    private static final String RESOURCES_PATH = "/resources";
    private static final String ROOT_PATH = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Redirects requests to front controller servlet
     * except requests to resources {@code RESOURCES_PATH} and {@code ROOT_PATH}
     * @param servletRequest the {@code ServletRequest} object contains the client's request
     * @param servletResponse the {@code ServletResponse} object contains the filter's response
     * @param filterChain the {@code FilterChain} for invoking the next filter or the resource
     * @throws IOException if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith(RESOURCES_PATH) || requestURI.equals(ROOT_PATH)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(SERVLET_PATH + requestURI)
                   .forward(request, response);
        }
    }
}
