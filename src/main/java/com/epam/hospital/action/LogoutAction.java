package com.epam.hospital.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.action.LoginAction.SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;

/**
 * The class of actions when the user quits the system
 */

public class LogoutAction extends AbstractAction {
    private static final String URI = "logout";
    private static final String ROOT_PATH = "/";

    public LogoutAction() {
        super(URI);
    }

    /**
     * Describes actions when the user quits the system
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER);
        return FORWARD_VIEW_PREFIX.getPrefix() + ROOT_PATH;
    }
}
