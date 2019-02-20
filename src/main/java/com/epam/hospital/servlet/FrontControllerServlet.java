package com.epam.hospital.servlet;

import com.epam.hospital.dao.ConnectionPool;
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
import static com.epam.hospital.util.PropertiesUtil.getMaxConn;
import static com.epam.hospital.util.PropertiesUtil.getPassword;
import static com.epam.hospital.util.ViewPrefixType.getValueByPrefix;

public class FrontControllerServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(FrontControllerServlet.class);
    private static final String CONTEXT_PARAMETER_FOR_DB_PROPERTIES = "dbPropertiesFile";
    private static final String JSON_MIMETYPE = "application/json;charset=UTF-8";

    private ActionFactory actionFactory;
    private ConnectionPool connectionPool;

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

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = this.actionFactory.getAction(request);
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
                LOG.error("Action is not defined for URI:" + request.getRequestURI());
                throw new IllegalStateException("Action is not defined for URI:" + request.getRequestURI());
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
