package com.epam.hospital.web;

import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.util.xmlparser.ParseUtil;
import com.epam.hospital.web.action.AbstractActionWithController;
import com.epam.hospital.web.action.Action;
import com.epam.hospital.web.action.ActionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import static com.epam.hospital.util.PropertiesUtil.*;

public class AppServletContextListner implements ServletContextListener {
    public static final String CONTEXT_PARAMETER_FOR_ACTION_FACTORY = "actionFactory";
    public static final String CONTEXT_PARAMETER_FOR_ERROR_MESSAGE = "errorMessage";
    public static final String SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER = "authorizedUser";

    private static final String CONTEXT_PARAMETER_FOR_CONNECTION_POOL = "connectionPool";
    private static final String CONTEXT_PARAMETER_FOR_ACTION_CLASSES = "actionClasses";
    private static final String CONTEXT_PARAMETER_FOR_DB_PROPERTIES = "dbPropertiesFile";
    private static final String CONTEXT_PARAMETER_FOR_CONTROLLERS_XMLFILE = "controllersXmlFile";


    private static final String REGEX_FOR_ACTION_CLASS_NAMES_STRING = "\\n\\s*";

    private static final Logger LOG = Logger.getLogger(AppServletContextListner.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        putInContextControllers(sce);
        putInContextActionFactory(sce);
    }

    private ConnectionPool putInContextConnectionPool(ServletContextEvent sce) {
        ConnectionPool connectionPool = (ConnectionPool) sce.getServletContext()
                .getAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL);
        if (connectionPool == null) {
            ServletContext servletContext = sce.getServletContext();
            try {
                String dbPropertiesFileName = servletContext.getInitParameter(CONTEXT_PARAMETER_FOR_DB_PROPERTIES);
                Properties properties = getProperties(dbPropertiesFileName);
                String driverName = getDriverName(properties);
                String url = getUrl(properties);
                String userName = getUserName(properties);
                String password = getPassword(properties);
                int maxConn = getMaxConn(properties);
                connectionPool = ConnectionPool.getInstance(driverName, url, userName, password, maxConn);
                servletContext.setAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL, connectionPool);
            } catch (IOException e) {
                LOG.error(e.getClass().getName() + " was thrown at initialization of the connection pool");
                servletContext.setAttribute(CONTEXT_PARAMETER_FOR_ERROR_MESSAGE, "There is no database connection");
                connectionPool = null;
            }
        }
        return connectionPool;
    }

    private void putInContextControllers(ServletContextEvent sce) {
        ConnectionPool connectionPool = putInContextConnectionPool(sce);
        ServletContext servletContext = sce.getServletContext();
        if (connectionPool != null) {
            String controllersXmlFile = servletContext.getInitParameter(CONTEXT_PARAMETER_FOR_CONTROLLERS_XMLFILE);
            Map<String, Object> controllers = ParseUtil.getControllers(controllersXmlFile, connectionPool);
            if (controllers != null) {
                for (Map.Entry<String, Object> pair : controllers.entrySet()) {
                    servletContext.setAttribute(pair.getKey(), pair.getValue());
                }
            } else {
                servletContext.setAttribute(CONTEXT_PARAMETER_FOR_ERROR_MESSAGE, "Error of project initialization");
            }
        }
    }

    private void putInContextActionFactory(ServletContextEvent sce) {
        ActionFactory actionFactory = ActionFactory.getInstance();
        ServletContext servletContext = sce.getServletContext();
        String actionClassNamesString = sce.getServletContext().getInitParameter(CONTEXT_PARAMETER_FOR_ACTION_CLASSES);
        String[] actionClassNames = actionClassNamesString.split(REGEX_FOR_ACTION_CLASS_NAMES_STRING);
        for (String actionClassName : actionClassNames) {
            Class<?> actionClass = null;
            try {
                actionClass = Class.forName(actionClassName);
                Action action = (Action) actionClass.getConstructor().newInstance();
                if (action instanceof AbstractActionWithController) {
                    AbstractActionWithController actionWithController = (AbstractActionWithController) action;
                    Object controller = servletContext.getAttribute(actionWithController.getControllerXmlId());
                    if (controller == null) {
                        LOG.error("Controller is null for action class:" + actionClassName);
                        throw new IllegalArgumentException("Controller is null for action class:" + actionClassName);
                    }
                    Method method = actionClass.getDeclaredMethod("setController", Object.class);
                    method.setAccessible(true);
                    method.invoke(actionWithController, controller);
                }
                actionFactory.addAction(action.getUri(), action);
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException |
                     NoSuchMethodException | ClassNotFoundException |
                     InstantiationException | NullPointerException e) {
                LOG.error(e.getClass().getName() + " was thrown at initialization of action class:" + actionClassName);
                actionFactory = null;
                break;
            }
        }
        if (actionFactory != null) {
            servletContext.setAttribute(CONTEXT_PARAMETER_FOR_ACTION_FACTORY, actionFactory);
        } else {
            servletContext.setAttribute(CONTEXT_PARAMETER_FOR_ERROR_MESSAGE, "Error of project initialization");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool connectionPool = (ConnectionPool) sce.getServletContext()
                .getAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL);
        if (connectionPool != null) {
            connectionPool.release();
        }
    }
}
