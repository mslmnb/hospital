package com.epam.hospital.util;

import com.epam.hospital.model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class ActionUtil {
    public static final String JSON_MIMETYPE = "application/json";
    public static final String TEXT_MIMETYPE = "text/html";

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
                "\"surName\": \"" + patient.getSurname() + "\" " +
                "}";
    }

}
