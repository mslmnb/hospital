package com.epam.hospital.action;

import com.epam.hospital.service.StaffService;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.filter.LangFilter.LANG_ATTRIBUTE_NAME;
import static com.epam.hospital.service.StaffService.*;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_STAFF_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class StaffAction extends AbstractActionWithService {
    private static final Logger LOG = Logger.getLogger(StaffAction.class);

    private static final String URI = "staff";
    private static final String JSP_FILE_NAME = "/jsp/staff.jsp";

    private StaffService service;

    public StaffAction() {
        super(URI, CONTEXT_PARAMETER_FOR_STAFF_SERVICE);
    }

    private void setService(Object service) {
        this.service = (StaffService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = getDirection(request.getPathInfo(), URI);
        String lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);

        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(lang));
                break;
            case SAVE:
                String idAsString = request.getParameter(ID_PARAMETER);
                String name = request.getParameter(NAME_PARAMETER);
                String additionalNAme = request.getParameter(ADDITIONAL_NAME_PARAMETER);
                String surname = request.getParameter(SURNAME_PARAMETER);
                String positionIdAsString = request.getParameter(POSITION_ID_PARAMETER);
                try {
                    service.save(idAsString, name, additionalNAme, surname, positionIdAsString);
                    result = "";
                } catch (AppException e) {
                    response.setStatus(422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            case GET:
                result = JSON_VIEW_PREFIX.getPrefix() +
                        getJsonViewForGetDirection(request, response, service, ID_PARAMETER);
                break;
            case DELETE:
                result = JSON_VIEW_PREFIX.getPrefix() +
                        getJsonViewForDeleteDirection(request, response, service, ID_PARAMETER);
                break;
            default:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonViewForDefaultDirection(response, LOG, direction);
                break;
        }
        return result;
    }
}
