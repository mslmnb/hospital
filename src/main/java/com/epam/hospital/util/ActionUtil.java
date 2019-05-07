package com.epam.hospital.util;

import com.epam.hospital.model.HavingJsonView;
import com.epam.hospital.service.CommonServiceOperationForBaseEntity;
import com.epam.hospital.util.exception.AppException;
import com.epam.hospital.util.exception.IndefiniteActionException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class of utilities for action's classes
 */
public class ActionUtil {

    /**
     * the possible directions
     */
    public static final String FORWARD_TO_JSP = "";
    public static final String GET_ALL = "/all";
    public static final String GET_ALL_ITEM_TRANSLATIONS = "/translation";
    public static final String SAVE = "/save";
    public static final String GET = "/get";
    public static final String DELETE = "/delete";

    /**
     * response status 422
     */
    public static final Integer RESPONSE_STATUS_422 = 422;

    public static String getJsonString(List<? extends HavingJsonView> elements) {
        return "[ "
                + elements.stream().map(el -> el.getJsonString()).collect(Collectors.joining(", "))
                + "]";
    }

    /**
     * Gets the direction from specific path
     * @param pathInfo the path includes {@code actionURI} and direction
     * @param actionURI the action's URI
     * @return the direction
     */
    public static String getDirection(String pathInfo, String actionURI) {
        return pathInfo.substring(actionURI.length() + 1);
    }

    /**
     * Deletes specific record and returns json data as view
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @param response the {@code HttpServletResponse} object contains the filter's response
     * @param service the service of operations with {@code BaseEntity}
     * @param idParameter the key of deleted record as string
     * @return empty string if the record was deleted from the table, else
     *     json data with error messages
     */
    public static String getJsonViewForDeleteDirection(HttpServletRequest request, HttpServletResponse response,
                                                       CommonServiceOperationForBaseEntity service, String idParameter) {
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

    /**
     * Gets specific record and returns json data as view
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @param response the {@code HttpServletResponse} object contains the filter's response
     * @param service the service of operations with {@code BaseEntity}
     * @param idParameter the key of record as string
     * @return the record as json data if the record was found, else
     *     json data with error messages
     */
    public static String getJsonViewForGetDirection(HttpServletRequest request, HttpServletResponse response,
                                                    CommonServiceOperationForBaseEntity service, String idParameter) {
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

    /**
     * Save specific record and returns json data as view
     * @param response the {@code HttpServletResponse} object contains the filter's response
     * @param service the service of operations with {@code BaseEntity}
     * @param args the data for the saved record
     * @return empty string if the record was saved into the table, else
     *     json data with error messages
     */
    public static String getJsonViewForSaveDirection(HttpServletResponse response,
                                                     CommonServiceOperationForBaseEntity service, String ... args) {
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

    /**
     * Registers an error message by specified logger
     * and throws {@code IndefiniteActionException}
     * @param request the {@code HttpServletRequest} object contains the client's request
     * @param logger the logger
     */
    public static void logAndThrowForIndefiniteActionException(HttpServletRequest request, Logger logger) {
        logger.error("Action is indefinite for URI:" + request.getRequestURI());
        throw new IndefiniteActionException("Action is indefinite for URI:" + request.getRequestURI());
    }

}
