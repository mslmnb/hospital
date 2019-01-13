package com.epam.hospital.web;

import com.epam.hospital.web.action.Action;
import com.epam.hospital.web.action.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_ACTION_FACTORY;

public class FrontControllerServlet extends HttpServlet {
    private static final String JSP_SUFFIX = ".jsp";
    private static final String AJAX_PREFIX = "app/ajax";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ActionFactory actionFactory = (ActionFactory) request.getServletContext()
                    .getAttribute(CONTEXT_PARAMETER_FOR_ACTION_FACTORY);
            Action action = actionFactory.getAction(request);
            String view = action.execute(request, response);

            if (request.getRequestURI().contains(AJAX_PREFIX)) { // for JSON data
                PrintWriter writer = response.getWriter();
                writer.print(view);
                writer.flush();

            } else if (view.contains(JSP_SUFFIX)) {             // for files with suffix .jsp
                setLangAttribute(request);
                request.getRequestDispatcher("/jsp/" + view).forward(request, response);
            } else {
                response.sendRedirect(view);
            }
        } catch (Exception e) {
            // insert logger
            throw new ServletException("Executing action failed.", e);
        }

    }

    private void setLangAttribute(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        if (lang == null) {
            lang = (String) request.getAttribute("lang");
            if (lang == null) {
                lang = request.getLocale().getLanguage();
            }
        }
        request.setAttribute("lang", lang);
    }
}
