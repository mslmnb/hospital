package com.epam.hospital.web;

import com.epam.hospital.util.ViewPrefixType;
import com.epam.hospital.web.action.Action;
import com.epam.hospital.web.action.ActionFactory;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_ACTION_FACTORY;
import static com.epam.hospital.util.ViewPrefixType.* ;

public class FrontControllerServlet extends HttpServlet {
    private static final String LANG_ATTRIBUTE_NAME = "lang";
    private static final String JSP_REQUEST_PREFIX = "/jsp/";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ActionFactory actionFactory = (ActionFactory) request.getServletContext()
                    .getAttribute(CONTEXT_PARAMETER_FOR_ACTION_FACTORY);
            Action action = actionFactory.getAction(request);
            String viewWithPrefix = action.execute(request, response);
            String view = getViewWithoutPrefix(viewWithPrefix);

            switch (getViewPrefix(viewWithPrefix)) {
                case JSON_VIEW_PREFIX:
                    PrintWriter writer = response.getWriter();
                    writer.print(view);
                    writer.flush();
                    break;
                case JSP_VIEW_PREFIX:
                    setLangAttribute(request);
                    request.getRequestDispatcher(JSP_REQUEST_PREFIX + view).forward(request, response);
                    break;
                case REDIRECT_VIEW_PREFIX:
                    response.sendRedirect(view);
                    break;
                default:
                    throw new IllegalStateException("Action is not defined for URI:" + request.getRequestURI());
            };
    }

    private void setLangAttribute(HttpServletRequest request) {
        String lang = request.getParameter(LANG_ATTRIBUTE_NAME);
        if (lang == null) {
            lang = (String) request.getAttribute(LANG_ATTRIBUTE_NAME);
            if (lang == null) {
                lang = request.getLocale().getLanguage();
            }
        }
        request.setAttribute(LANG_ATTRIBUTE_NAME, lang);
    }

    private ViewPrefixType getViewPrefix(String view) {
        String viewPrefix = view.substring(0, view.indexOf(":") + 1);
        return getValueByPrefix(viewPrefix);
    }

    private String getViewWithoutPrefix(String view) {
        return view.substring(view.indexOf(":") + 1);
    }
}
