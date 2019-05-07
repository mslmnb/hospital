package com.epam.hospital.action;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.jdbc.*;
import com.epam.hospital.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ActionFactory} instance is used to get action instances.
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
        UserService userService = new UserServiceImpl(new JdbcUserJdbcImpl(pool));
        PatientService patientService = new PatientServiceImpl(new JdbcPatientJdbcImpl(pool));
        addAction(new LoginAction(userService));
        addAction(new LogoutAction());
        addAction(new AdminAction());
        addAction(new NoAdminAction());
        addAction(new UserAction(userService));
        addAction(new StaffAction(new StaffServiceImpl(new JdbcStaffJdbcImpl(pool))));
        addAction(new RoleAction());
        addAction(new HandbkAction(new HandbkItemServiceImpl(new JdbcHandbkItemJdbcImpl(pool))));
        addAction(new TranslationAction(new TranslationServiceImpl(new JdbcTranslationJdbcImpl(pool))));
        addAction(new LangAction(new LangServiceImpl(new JdbcLangJdbcImpl(pool))));
        addAction(new PatientAction(patientService));
        addAction(new ReceptionAction(patientService));
        addAction(new PatientDiagnosisAction(new PatientDiagnosisServiceImpl(new JdbcPatientDiagnosisJdbcImpl(pool))));
        addAction(new PatientPrescriptionAction(new PatientPrescriptionServiceImpl(
                                                    new JdbcPatientPrescriptionJdbcImpl(pool)))
                                                );
        addAction(new PatientInspectionAction(new PatientInspectionServiceImpl(
                                                  new JdbcPatientInspectionJdbcImpl(pool)))
                                              );
    }

    /**
     * Returns the instance of the {@code ActionFactory} class
     * @param connectionPool the connection pool
     * @return the instance of the {@code ActionFactory} class
     */
    public static synchronized ActionFactory getInstance(ConnectionPool connectionPool) {
        if (instance == null) {
            instance = new ActionFactory(connectionPool);
        }
        return instance;
    }

    /**
     * Puts the specified the {@code AbstractAction} object to {@code actions}
     * @param action the {@code AbstractAction} object for for addition to {@code actions}
     */
    private void addAction(AbstractAction action) {
        actions.put(action.getUri(), action);
    }

    /**
     * Returns the {@code Action} object for specified client's request
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @return the {@code Action} object for specified client's request or null
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
