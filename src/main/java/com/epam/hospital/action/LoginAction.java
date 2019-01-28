package com.epam.hospital.action;

import com.epam.hospital.model.User;
import com.epam.hospital.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.REDIRECT_VIEW_PREFIX;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_USER_SERVICE;
import static com.epam.hospital.servlet.AppServletContextListner.SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER;

public class LoginAction extends AbstractActionWithService {
    private static final String URI = "login";

    private static final String VIEW_NAME = "patients";
    private static final String JSP_FILE_NAME_WITH_ERROR_PARAMETER = "/jsp/login.jsp?error=true";
    private static final String LOGIN_POST_PARAMETER = "login";
    private static final String PASSWORD_POST_PARAMETER = "password";

    private UserService service;

    public LoginAction() {
        super(URI, CONTEXT_PARAMETER_FOR_USER_SERVICE);
    }

    private void setService(Object service) {
        this.service = (UserService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;

        String login = request.getParameter(LOGIN_POST_PARAMETER);
        String password = request.getParameter(PASSWORD_POST_PARAMETER);

        User user = service.getByLogin(login);
        if (user!=null && user.getPassword().equals(password)) {
            user.setPassword(null);
            request.getSession().setAttribute(SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER, user);
            result = REDIRECT_VIEW_PREFIX.getPrefix() + VIEW_NAME;
        } else {
            result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME_WITH_ERROR_PARAMETER;
        }
        return result;
    }
}
