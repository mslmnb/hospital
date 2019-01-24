package com.epam.hospital.web.action;

import com.epam.hospital.model.User;
import com.epam.hospital.to.AuthorizedUser;
import com.epam.hospital.web.patient.PatientController;
import com.epam.hospital.web.user.UserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.REDIRECT_VIEW_PREFIX;
import static com.epam.hospital.web.AppServletContextListner.SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER;

public class LoginAction extends AbstractActionWithController {
    private static final String URI = "login";
    private static final String CONTROLLER_XML_ID = "userController";

    private static final String VIEW_NAME = "patients";
    private static final String JSP_FILE_NAME_WITH_ERROR_PARAMETER = "/jsp/login.jsp?error=true";
    private static final String LOGIN_POST_PARAMETER = "login";
    private static final String PASSWORD_POST_PARAMETER = "password";

    private UserController controller;

    public LoginAction() {
        super(URI, CONTROLLER_XML_ID);
    }

    private void setController(Object controller) {
        this.controller = (UserController) controller;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;

        String login = request.getParameter(LOGIN_POST_PARAMETER);
        String password = request.getParameter(PASSWORD_POST_PARAMETER);

        User user = controller.getByLoginWithOutRoles(login);
        if (user!=null && user.getPassword().equals(password)) {
            request.getSession().setAttribute(SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER, new AuthorizedUser(user));
            result = REDIRECT_VIEW_PREFIX.getPrefix() + VIEW_NAME;
        } else {
            result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME_WITH_ERROR_PARAMETER;
        }
        return result;
    }
}
