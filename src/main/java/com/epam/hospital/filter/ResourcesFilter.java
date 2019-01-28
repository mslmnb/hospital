package com.epam.hospital.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcesFilter implements Filter {
    private static final String SERVLET_PATH = "/app";
    private static final String RESOURCES_PATH = "/resources";
    private static final String ROOT_PATH = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

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
