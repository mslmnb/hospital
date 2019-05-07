package com.epam.hospital.servlet;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.ViewPrefixType;
import com.epam.hospital.action.Action;
import com.epam.hospital.action.ActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import static com.epam.hospital.util.PropertiesUtil.*;
import static com.epam.hospital.util.ViewPrefixType.getValueByPrefix;

/**
 * The front controller servlet processes all requests from client to server.
 */
public class FrontControllerServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(FrontControllerServlet.class);
    private static final String CONTEXT_PARAMETER_FOR_DB_PROPERTIES = "dbPropertiesFile";
    private static final String JSON_MIMETYPE = "application/json;charset=UTF-8";

    /**
     * the factory of {@code Action} classes
     */
    private ActionFactory actionFactory;

    /**
     * the connection pool to database
     */
    private ConnectionPool connectionPool;

    /**
     * Initializes the factory of {@code Action} classes and the connection pool
     * @throws ServletException if an exception occurs that interrupts the servlet's normal operation
     */
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        try {
            String dbPropertiesFileName = servletContext.getInitParameter(CONTEXT_PARAMETER_FOR_DB_PROPERTIES);
            Properties properties = getProperties(dbPropertiesFileName);
            String driverName = getDriverName(properties);
            String url = getUrl(properties);
            String userName = getUserName(properties);
            String password = getPassword(properties);
            int maxConn = getMaxConn(properties);
            this.connectionPool = ConnectionPool.getInstance(driverName, url, userName, password, maxConn);
            this.actionFactory = ActionFactory.getInstance(this.connectionPool);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Processes all requests. The request is analyzed and transferred in the {@code Action} processor.
     * The {@code Action} processor  return the view with prefix.
     * The prefix defines as view will be transferred to the client.
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @param response the {@code HttpServletResponse} object contains the filter's response
     * @throws ServletException if the HTTP request cannot be handled
     * @throws IOException if an input or output error occurs while the servlet is handling the HTTP request
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = this.actionFactory.getAction(request);
        if (action==null) {
            ActionUtil.logAndThrowForIndefiniteActionException(request, LOG);
        }
        String viewWithPrefix = action.execute(request, response);
        String view = getViewWithoutPrefix(viewWithPrefix);

        switch (getViewPrefix(viewWithPrefix)) {
            case JSON_VIEW_PREFIX:
                if (!view.isEmpty()) {
                    response.setContentType(JSON_MIMETYPE);
                    PrintWriter writer = response.getWriter();
                    writer.print(view);
                    writer.flush();
                }
                break;
            case FORWARD_VIEW_PREFIX:
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case REDIRECT_VIEW_PREFIX:
                response.sendRedirect(view);
                break;
            default:
                ActionUtil.logAndThrowForIndefiniteActionException(request, LOG);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (this.connectionPool != null) {
            this.connectionPool.release();
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
