package com.epam.hospital.action;

import com.epam.hospital.model.User;
import com.epam.hospital.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.REDIRECT_VIEW_PREFIX;

/**
 * The class of actions when the user logs in
 */

public class LoginAction extends AbstractAction {
    private static final String URI = "login";

    private static final String VIEW_NAME_FOR_NOADMIN = "noAdmin";
    private static final String VIEW_NAME_FOR_ADMIN = "admin";
    private static final String JSP_FILE_NAME_WITH_ERROR_PARAMETER = "/jsp/login.jsp?error=true";
    private static final String LOGIN_POST_PARAMETER = "login";
    private static final String PASSWORD_POST_PARAMETER = "password";
    public static final String SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER = "authorizedUser";

    private final UserService service;

    public LoginAction(UserService service) {
        super(URI);
        this.service = service;
    }

    /**
     * Describes actions when the user logs in
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;

        String login = request.getParameter(LOGIN_POST_PARAMETER);
        String password = request.getParameter(PASSWORD_POST_PARAMETER);

        User user = service.getByLogin(login);
        if (user!=null && user.getPassword().equals(DigestUtils.md5Hex(password))) {
            user.setPassword(null);
            request.getSession().setAttribute(SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER, user);
            if (user.isAdmin()) {
                result = REDIRECT_VIEW_PREFIX.getPrefix() + VIEW_NAME_FOR_ADMIN;
            } else {
                result = REDIRECT_VIEW_PREFIX.getPrefix() + VIEW_NAME_FOR_NOADMIN;
            }
        } else {
            result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME_WITH_ERROR_PARAMETER;
        }
        return result;
    }
}
