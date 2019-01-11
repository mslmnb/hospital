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

public class AppServletContextListner  implements ServletContextListener{
    public static final String CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER = "patientController";
    public static final String CONTEXT_PARAMETER_FOR_CONNECTION_POOL = "connectionPool";
    public static final String CONTEXT_PARAMETER_FOR_ACTION_CLASSES = "actionClasses";
    public static final String CONTEXT_PARAMETER_FOR_ACTION_FACTORY = "actionFactory";

    public static final String DRIVER_NAME = "driverName";
    public static final String URL = "url";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String MAX_CONN = "maxConn";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        putInContextControllers(sce);
        putInContextActionFactory(sce);
        int i = 0;
    }

    private ConnectionPool putInContextConnectionPool(ServletContextEvent sce) {
        ConnectionPool connectionPool = (ConnectionPool) sce.getServletContext()
                                                            .getAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL);
        if (connectionPool==null) {
            ServletContext context = sce.getServletContext();
            String driverName = context.getInitParameter(DRIVER_NAME);
            String url = context.getInitParameter(URL);
            String userName = context.getInitParameter(USER_NAME);
            String password = context.getInitParameter(PASSWORD);
            int maxConn = Integer.parseInt(context.getInitParameter(MAX_CONN));
            connectionPool = ConnectionPool.getInstance(driverName, url, userName, password, maxConn);
            sce.getServletContext().setAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL, connectionPool);
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
        String[] actionClassNames = actionClassNamesString.split("\\n\\s*");
        for (String actionClassName: actionClassNames ) {
            try {
                Class<?> actionClass = Class.forName(actionClassName);
                Action action = (Action) actionClass.getConstructor().newInstance();
                actionFactory.addAction(action.getUri(), action);
            } catch (Exception e) {
                // insert logger
                e.printStackTrace();
            }
        }
        sce.getServletContext().setAttribute(CONTEXT_PARAMETER_FOR_ACTION_FACTORY, actionFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool connectionPool = (ConnectionPool)sce.getServletContext()
                                                           .getAttribute(CONTEXT_PARAMETER_FOR_CONNECTION_POOL);
        if (connectionPool!=null) {
            connectionPool.release();
        }
    }

}
