package com.epam.hospital.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LangFilter implements Filter {
    private static final String LANG_ATTRIBUTE_NAME = "lang";
    public static final String UTF8_CODE = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        setLangAttribute(request);
        response.setCharacterEncoding(UTF8_CODE);

        filterChain.doFilter(request, response);
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
