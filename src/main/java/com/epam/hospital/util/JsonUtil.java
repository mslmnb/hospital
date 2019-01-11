package com.epam.hospital.util;

import com.epam.hospital.to.PatientTo;

import java.util.List;

public class JsonUtil {
    public static final String JSON_MIMETYPE = "application/json";
    public static final String AJAX_URI_PREFIX = "/app/ajax";
    public static final String UTF8_CODE = "UTF-8";



    public static String getJsonString(List<PatientTo> patients) {  // перенести в JsonUtil class
        StringBuilder result = new StringBuilder("[ ");
        for (PatientTo patientTo : patients) {
            result.append(patientTo.toString() + ", ");
        }
        result = result.deleteCharAt(result.length()-2).append("]");
        return result.toString();
    }

}
