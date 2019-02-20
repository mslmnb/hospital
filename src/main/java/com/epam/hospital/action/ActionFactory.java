package com.epam.hospital.action;

import com.epam.hospital.dao.ConnectionPool;
import com.epam.hospital.dao.jdbc.*;
import com.epam.hospital.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static ActionFactory instance;
    private final Map<String, AbstractAction> actions = new HashMap<>();

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

    public static synchronized ActionFactory getInstance(ConnectionPool connectionPool) {
        if (instance == null) {
            instance = new ActionFactory(connectionPool);
        }
        return instance;
    }

    private void addAction(AbstractAction action) {
        actions.put(action.getUri(), action);
    }

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
