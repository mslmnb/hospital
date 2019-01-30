package com.epam.hospital.util;

import com.epam.hospital.model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class ActionUtil {
    public static final String JSON_MIMETYPE = "application/json;charset=UTF-8";
    public static final String FORWARD_TO_JSP = "";
    public static final String GET_ALL = "/all";
    public static final String SAVE = "/save";

    public static final String AJAX_URI_PREFIX = "/app/ajax";

    public static String getJsonString(List<Patient> patients) {  // перенести в JsonUtil class
        return "[ "
                + patients.stream().map(patient -> getJsonString(patient)).collect(Collectors.joining(", "))
                + "]";
    }

    private static String getJsonString (Patient patient) {
        return "{ " +
                "\"id\": " + patient.getId() + ", " +
                "\"name\": \"" + patient.getName() + "\", " +
                "\"additionalName\": \"" + patient.getAdditionalName() + "\", " +
                "\"surName\": \"" + patient.getSurname() + "\", " +
                "\"phone\": \"" + patient.getPhone() + "\" " +
                "}";
    }

    public static String getDirection(String pathInfo, String actionURI) {
        return pathInfo.substring(actionURI.length()+1);
    }
}
