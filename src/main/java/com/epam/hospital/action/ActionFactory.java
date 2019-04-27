package com.epam.hospital.action;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.jdbc.*;
import com.epam.hospital.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The ActionFactory is used for returning action instances.
 */

public class ActionFactory {
    private static ActionFactory instance;

    /**
     * the action map
     */
    private final Map<String, AbstractAction> actions = new HashMap<>();

    /**
     * Constructs instance of the ActionFactory and initializes the action map
     * @param pool the connection pool
     */
    private ActionFactory(ConnectionPool pool) {
        UserService userService = new UserServiceImpl(new JdbcUserDAOImpl(pool));
        PatientService patientService = new PatientServiceImpl(new JdbcPatientDAOImpl(pool));
        addAction(new LoginAction(userService));
        addAction(new LogoutAction());
        addAction(new AdminAction());
        addAction(new NoAdminAction());
        addAction(new UserAction(userService));
        addAction(new StaffAction(new StaffServiceImpl(new JdbcStaffDAOImpl(pool))));
        addAction(new RoleAction());
        addAction(new HandbkAction(new HandbkItemServiceImpl(new JdbcHandbkItemDAOImpl(pool))));
        addAction(new TranslationAction(new TranslationServiceImpl(new JdbcTranslationDAOImpl(pool))));
        addAction(new LangAction(new LangServiceImpl(new JdbcLangDAOImpl(pool))));
        addAction(new PatientAction(patientService));
        addAction(new ReceptionAction(patientService));
        addAction(new PatientDiagnosisAction(new PatientDiagnosisServiceImpl(new JdbcPatientDiagnosisDAOImpl(pool))));
        addAction(new PatientPrescriptionAction(new PatientPrescriptionServiceImpl(
                                                    new JdbcPatientPrescriptionDAOImpl(pool)))
                                                );
        addAction(new PatientInspectionAction(new PatientInspectionServiceImpl(
                                                  new JdbcPatientInspectionDAOImpl(pool)))
                                              );
    }

    /**
     * Returns the instance of the ActionFactory class
     * @param connectionPool the connection pool
     * @return the instance of the ActionFactory class
     */
    public static synchronized ActionFactory getInstance(ConnectionPool connectionPool) {
        if (instance == null) {
            instance = new ActionFactory(connectionPool);
        }
        return instance;
    }

    /**
     * Puts the specified action to action map
     * @param action the action
     */
    private void addAction(AbstractAction action) {
        actions.put(action.getUri(), action);
    }

    /**
     * Returns the action for specified request
     * @param request the http request
     * @return the action for specified request
     */
    public Action getAction(HttpServletRequest request) {
        Action result = null;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        for (Map.Entry pair : actions.entrySet()) {
            if (requestURI.contains((CharSequence) pair.getKey())) {
                result = (Action) pair.getValue();
            }
        }
        return result;
    }
}
