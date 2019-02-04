package com.epam.hospital.util;

import com.epam.hospital.model.HavingJsonView;
import com.epam.hospital.model.Patient;
import com.epam.hospital.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ActionUtil {
    public static final String FORWARD_TO_JSP = "";
    public static final String GET_ALL = "/all";
    public static final String SAVE = "/save";
    public static final String GET = "/get";
    public static final String DELETE = "/delete";

    public static String getJsonString(List<? extends HavingJsonView> elements) {  // перенести в JsonUtil class
        return "[ "
                + elements.stream().map(el -> el.getJsonString()).collect(Collectors.joining(", "))
                + "]";
    }

    public static String getDirection(String pathInfo, String actionURI) {
        return pathInfo.substring(actionURI.length()+1);
    }
}
