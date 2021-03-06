package com.epam.hospital.action;

import com.epam.hospital.service.UserService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.UserServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

/**
 * The class of actions for work with the information about the users of the system
 */

public class UserAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(UserAction.class);

    private static final String URI = "users";
    private static final String JSP_FILE_NAME = "/jsp/users.jsp";

    private final UserService service;

    public UserAction(UserService service) {
        super(URI);
        this.service = service;
    }

    /**
     * Describes actions for work with the information about the users of the system
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
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
                result = JSON_VIEW_PREFIX.getPrefix() +
                         getJsonViewForSaveDirection(response, service, idAsString,
                                                     staffIdAsString, login, password, roleAsString);
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
                ActionUtil.logAndThrowForIndefiniteActionException(request, LOG);
                break;
        }
        return result;
    }
}
