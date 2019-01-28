package com.epam.hospital.servlet;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.jdbc.JdbcPatientDAOImpl;
import com.epam.hospital.dao.jdbc.JdbcUserDAOImpl;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.PatientServiceImpl;
import com.epam.hospital.service.UserService;
import com.epam.hospital.service.UserServiceImpl;
import com.epam.hospital.action.AbstractActionWithService;
import com.epam.hospital.action.Action;
import com.epam.hospital.action.ActionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;

import static com.epam.hospital.util.PropertiesUtil.*;

public class AppServletContextListner implements ServletContextListener {
    public static final String CONTEXT_PARAMETER_FOR_ACTION_FACTORY = "actionFactory";
    public static final String CONTEXT_PARAMETER_FOR_ERROR_MESSAGE = "errorMessage";

    public static final String CONTEXT_PARAMETER_FOR_PATIENT_SERVICE = "patientService";
    public static final String CONTEXT_PARAMETER_FOR_USER_SERVICE = "userService";

    public static final String SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER = "authorizedUser";

    private static final String CONTEXT_PARAMETER_FOR_CONNECTION_POOL = "connectionPool";
    private static final String CONTEXT_PARAMETER_FOR_ACTION_CLASSES = "actionClasses";
    private static final String CONTEXT_PARAMETER_FOR_DB_PROPERTIES = "dbPropertiesFile";
    private static final String SET_SERVICE_METHOD = "setService";


    private static final String REGEX_FOR_ACTION_CLASS_NAMES_STRING = "\\n\\s*";

    private static final Logger LOG = Logger.getLogger(AppServletContextListner.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        putInContextServices(sce);
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

    private void putInContextServices(ServletContextEvent sce) {
        ConnectionPool connectionPool = putInContextConnectionPool(sce);
        ServletContext servletContext = sce.getServletContext();
        if (connectionPool != null) {
            PatientService patientService = new PatientServiceImpl(new JdbcPatientDAOImpl(connectionPool));
            UserService userService = new UserServiceImpl(new JdbcUserDAOImpl(connectionPool));
            servletContext.setAttribute(CONTEXT_PARAMETER_FOR_PATIENT_SERVICE, patientService);
            servletContext.setAttribute(CONTEXT_PARAMETER_FOR_USER_SERVICE, userService);
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
                if (action instanceof AbstractActionWithService) {
                    AbstractActionWithService actionWithService = (AbstractActionWithService) action;
                    Object service = servletContext.getAttribute(actionWithService.getContextParameterForService());
                    if (service == null) {
                        LOG.error("Service is null for action class:" + actionClassName);
                        throw new IllegalArgumentException("Service is null for action class:" + actionClassName);
                    }
                    Method method = actionClass.getDeclaredMethod(SET_SERVICE_METHOD, Object.class);
                    method.setAccessible(true);
                    method.invoke(actionWithService, service);
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
