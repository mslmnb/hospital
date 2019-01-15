package com.epam.hospital.util;

import com.epam.hospital.to.PatientTo;

import java.util.List;
import java.util.stream.Collectors;

public class ActionUtil {
    public static final String JSON_MIMETYPE = "application/json";
    public static final String TEXT_MIMETYPE = "text/html";
    public static final String UTF8_CODE = "UTF-8";

    public static final String AJAX_URI_PREFIX = "/app/ajax";

    public static String getJsonString(List<PatientTo> patients) {  // перенести в JsonUtil class
        return "[ "
                + patients.stream().map(patientTo -> patientTo.getJsonString()).collect(Collectors.joining(", "))
                + "]";
    }

}
