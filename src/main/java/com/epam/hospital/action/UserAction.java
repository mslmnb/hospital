package com.epam.hospital.action;

import com.epam.hospital.service.UserService;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.UserService.*;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_USER_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class UserAction extends AbstractActionWithService {
    private static final Logger LOG = Logger.getLogger(UserAction.class);

    private static final String URI = "users";
    private static final String JSP_FILE_NAME = "/jsp/users.jsp";

    private UserService service;

    public UserAction() {
        super(URI, CONTEXT_PARAMETER_FOR_USER_SERVICE);
    }

    private void setService(Object service) {
        this.service = (UserService) service;
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
                String idAsString = request.getParameter(ID_PARAMETER);
                String staffIdAsString = request.getParameter(STAFF_ID_PARAMETER);
                String login = request.getParameter(LOGIN_PARAMETER);
                String roleAsString = request.getParameter(ROLE_PARAMETER);
                String password = request.getParameter(PASSWORD_PARAMETER);
                try {
                    service.save(idAsString, staffIdAsString, login, password, roleAsString);
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
