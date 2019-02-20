package com.epam.hospital.util;

import com.epam.hospital.model.HavingJsonView;
import com.epam.hospital.service.HavingDeleteMethod;
import com.epam.hospital.service.HavingGetMethod;
import com.epam.hospital.service.HavingSaveMethod;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class ActionUtil {
    public static final String FORWARD_TO_JSP = "";
    public static final String GET_ALL = "/all";
    public static final String GET_ALL_ITEM_TRANSLATIONS = "/translation";
    public static final String SAVE = "/save";
    public static final String GET = "/get";
    public static final String DELETE = "/delete";
    public static final Integer RESPONSE_STATUS_422 = 422;

    public static String getJsonString(List<? extends HavingJsonView> elements) {
        return "[ "
                + elements.stream().map(el -> el.getJsonString()).collect(Collectors.joining(", "))
                + "]";
    }

    public static String getDirection(String pathInfo, String actionURI) {
        return pathInfo.substring(actionURI.length() + 1);
    }

    public static String getJsonViewForDeleteDirection(HttpServletRequest request, HttpServletResponse response,
                                                       HavingDeleteMethod service, String idParameter) {
        String result;
        String idAsString = request.getParameter(idParameter);
        try {
            service.delete(idAsString);
            result = "";
        } catch (AppException e) {
            response.setStatus(RESPONSE_STATUS_422);
            result = e.getCheckResult().getJsonString();
        }
        return result;
    }

    public static String getJsonViewForGetDirection(HttpServletRequest request, HttpServletResponse response,
                                                    HavingGetMethod service, String idParameter) {
        String result;
        try {
            String idAsString = request.getParameter(idParameter);
            result = service.get(idAsString).getJsonString();
        } catch (AppException e) {
            response.setStatus(RESPONSE_STATUS_422);
            result = e.getCheckResult().getJsonString();
        }
        return result;
    }

    public static String getJsonViewForDefaultDirection(HttpServletResponse response, Logger logger, String direction) {
        logger.error("Actions are not defined for direction: " + direction);
        response.setStatus(RESPONSE_STATUS_422);
        return new CheckResult(UNKNOWN_ERROR).getJsonString();
    }

    public static String getJsonViewForSaveDirection(HttpServletResponse response,
                                                     HavingSaveMethod service, String ... args) {
        String result;
        try {
            service.save(args);
            result = "";
        } catch(AppException e) {
            response.setStatus(RESPONSE_STATUS_422);
            result = e.getCheckResult().getJsonString();
        }
        return result;
    }

}
