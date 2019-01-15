package com.epam.hospital.web;

import com.epam.hospital.repository.ConnectionPool;
import com.epam.hospital.repository.PatientRepository;
import com.epam.hospital.repository.jdbc.JdbcPatientRepositoryImpl;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.service.PatientServiceImpl;
import com.epam.hospital.web.action.Action;
import com.epam.hospital.web.action.ActionFactory;
import com.epam.hospital.web.patient.PatientController;
import com.epam.hospital.web.patient.PatientControllerImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

import static com.epam.hospital.util.PropertiesUtil.*;

public class AppServletContextListner implements ServletContextListener {
    public static final String CONTEXT_PARAMETER_FOR_ACTION_FACTORY = "actionFactory";
    public static final String CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER = "patientController";

    private static final String CONTEXT_PARAMETER_FOR_CONNECTION_POOL = "connectionPool";
    private static final String CONTEXT_PARAMETER_FOR_ACTION_CLASSES = "actionClasses";
    private static final String CONTEXT_PARAMETER_FOR_DB_PROPERTIES = "dbPropertiesFile";

    private static final String REGEX_FOR_ACTION_CLASS_NAMES_STRING = "\\n\\s*";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        putInContextControllers(sce);
        putInContextActionFactory(sce);
    }

    private ConnectionPool putInContextConnectionPool(ServletContextEvent sce) {
        ConnectionPool connectionPool = (ConnectionPool) sce.getServletContext()
                .getAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL);
        if (connectionPool == null) {
            ServletContext context = sce.getServletContext();
            try {
                String dbPropertiesFileName = context.getInitParameter(CONTEXT_PARAMETER_FOR_DB_PROPERTIES);
                Properties properties = getProperties(dbPropertiesFileName);
                String driverName = getDriverName(properties);
                String url = getUrl(properties);
                String userName = getUserName(properties);
                String password = getPassword(properties);
                int maxConn = getMaxConn(properties);
                connectionPool = ConnectionPool.getInstance(driverName, url, userName, password, maxConn);
            } catch (IOException e) {
                connectionPool = null;
            }
            context.setAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL, connectionPool);
        }
        return connectionPool;
    }

    private void putInContextControllers(ServletContextEvent sce) {
        ConnectionPool connectionPool = putInContextConnectionPool(sce);
        PatientRepository repository = new JdbcPatientRepositoryImpl(connectionPool);
        PatientService service = new PatientServiceImpl(repository);
        PatientController controller = new PatientControllerImpl(service);
        sce.getServletContext().setAttribute(CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER, controller);
    }

    private void putInContextActionFactory(ServletContextEvent sce) {
        ActionFactory actionFactory = ActionFactory.getInstance();
        String actionClassNamesString = sce.getServletContext().getInitParameter(CONTEXT_PARAMETER_FOR_ACTION_CLASSES);
        String[] actionClassNames = actionClassNamesString.split(REGEX_FOR_ACTION_CLASS_NAMES_STRING);
        for (String actionClassName : actionClassNames) {
            Class<?> actionClass = null;
            try {
                actionClass = Class.forName(actionClassName);
                Action action = (Action) actionClass.getConstructor().newInstance();
                actionFactory.addAction(action.getUri(), action);
            } catch (Exception e) {
            }
        }
        sce.getServletContext().setAttribute(CONTEXT_PARAMETER_FOR_ACTION_FACTORY, actionFactory);
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
