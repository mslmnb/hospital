package com.epam.hospital.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The filter sets UTF-8 code for all requests to a application and responses from a application.
 * Also it sets session's attribute for language.
 */

public class LangFilter implements Filter {
    public static final String LANG_ATTRIBUTE_NAME = "lang";
    private static final String UTF8_CODE = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Sets UTF-8 code for all requests to a application and responses from a application.
     * Also sets session's attribute for language.
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

        response.setCharacterEncoding(UTF8_CODE);
        request.setCharacterEncoding(UTF8_CODE);
        setLangAttribute(request);

        filterChain.doFilter(request, response);
    }

    /**
     * Sets session's attribute for language.
     * @param request the {@code HttpServletRequest} object contains the client's request
     */
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
