package com.epam.hospital.web.action;

import com.epam.hospital.model.User;
import com.epam.hospital.web.user.UserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_AUTHORIZED_USER;
import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_USER_CONTROLLER;

public class LoginAction extends AbstractAction {
    private static final String ATTRIBUTE_NAME = "";
    private static final String URI = "login";
    private static final String JSP_FILE = "";

    public LoginAction() {
        super(ATTRIBUTE_NAME, URI, JSP_FILE);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String requestURI = request.getRequestURI().substring(request.getContextPath().length());

        UserController controller = (UserController) request.getServletContext()
                                                            .getAttribute(CONTEXT_PARAMETER_FOR_USER_CONTROLLER);
        User user = controller.getByLogin(login);
        if (user!=null && user.getPassword().equals(password)) {
            request.getServletContext().setAttribute(CONTEXT_PARAMETER_FOR_AUTHORIZED_USER, user);
            request.getSession().setAttribute("autorizedUser", user); //jsp должна иметт loginedUser
            result = "redirect:patients";
        } else {
            result = "jsp:login.jsp?error=true";
        }
        return result;
    }
}
