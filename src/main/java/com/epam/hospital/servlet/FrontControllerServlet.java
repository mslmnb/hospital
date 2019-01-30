package com.epam.hospital.servlet;

import com.epam.hospital.util.ViewPrefixType;
import com.epam.hospital.action.Action;
import com.epam.hospital.action.ActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.epam.hospital.util.ViewPrefixType.getValueByPrefix;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_ACTION_FACTORY;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_ERROR_MESSAGE;

public class FrontControllerServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(FrontControllerServlet.class);
    private String errorMessage;

    @Override
    public void init() throws ServletException {
        super.init();
        errorMessage = (String) getServletContext().getAttribute(CONTEXT_PARAMETER_FOR_ERROR_MESSAGE);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            if (errorMessage != null) {
                LOG.error(errorMessage);
                throw new IllegalStateException(errorMessage);
            }
            ActionFactory actionFactory = (ActionFactory) getServletContext()
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
                case FORWARD_VIEW_PREFIX:
                    request.getRequestDispatcher(view).forward(request, response);
                    break;
                case REDIRECT_VIEW_PREFIX:
                    response.sendRedirect(view);
                    break;
                default:
                    LOG.error("Action is not defined for URI:" + request.getRequestURI());
                    throw new IllegalStateException("Action is not defined for URI:" + request.getRequestURI());
            }
    }

    private ViewPrefixType getViewPrefix(String view) {
        String viewPrefix = view.substring(0, view.indexOf(":") + 1);
        return getValueByPrefix(viewPrefix);
    }

    private String getViewWithoutPrefix(String view) {
        return view.substring(view.indexOf(":") + 1);
    }
}