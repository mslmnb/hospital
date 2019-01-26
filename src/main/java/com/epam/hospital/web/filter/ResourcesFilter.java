package com.epam.hospital.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.hospital.util.ActionUtil.UTF8_CODE;

public class ResourcesFilter implements Filter {
    private static final String LANG_ATTRIBUTE_NAME = "lang";
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
        setLangAttribute(request); //move to LangFilter
        response.setCharacterEncoding(UTF8_CODE); // //move to LangFilter

        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith(RESOURCES_PATH) || requestURI.equals(ROOT_PATH)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(SERVLET_PATH + requestURI)
                   .forward(request, response);
        }
    }

    private void setLangAttribute(HttpServletRequest request) {
        String lang = request.getParameter(LANG_ATTRIBUTE_NAME);
        if (lang == null) {
            lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);
            if (lang == null) {
                lang = request.getLocale().getLanguage();
                request.getSession().setAttribute(LANG_ATTRIBUTE_NAME, lang);
            }
        } else {
            request.getSession().setAttribute(LANG_ATTRIBUTE_NAME, lang);
        }
    }

}
