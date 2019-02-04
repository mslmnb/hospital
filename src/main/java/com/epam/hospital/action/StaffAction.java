package com.epam.hospital.action;

import com.epam.hospital.service.StaffService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_STAFF_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ActionUtil.SAVE;
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
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);

        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll());
                break;
            case SAVE:
                result = JSON_VIEW_PREFIX.getPrefix(); //+ result;
                break;
            default:
                LOG.error("Actions are not defined for direction: " + direction);
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
        }
        return result;




    }
}
