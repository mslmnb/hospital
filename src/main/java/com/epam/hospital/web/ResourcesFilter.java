package com.epam.hospital.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ResourcesFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith("/resources") || requestURI.equals("/")) {
            filterChain.doFilter(request, servletResponse);
        } else {
            request.getRequestDispatcher("/app" + requestURI).forward(request, servletResponse);
        }
    }

}
