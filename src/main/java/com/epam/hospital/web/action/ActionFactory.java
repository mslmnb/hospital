package com.epam.hospital.web.action;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static ActionFactory instance;
    private final Map<String, Action> actions = new HashMap<>();

    private ActionFactory() {
    }

    public static synchronized ActionFactory getInstance() {
        if (instance == null) {
            instance = new ActionFactory();
        }
        return instance;
    }

    public  void addAction(String key, Action action) {
        actions.put(key, action);
    }

    public  Action getAction(HttpServletRequest request) {
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
