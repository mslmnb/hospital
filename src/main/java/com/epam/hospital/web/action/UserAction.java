package com.epam.hospital.web.action;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.web.user.UserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class UserAction extends AbstractActionWithController {
    private static final String URI = "users";
    private static final String CONTROLLER_XML_ID = "userController";

    private UserController controller;

    public UserAction() {
        super(URI, CONTROLLER_XML_ID);
    }

    private void setController(Object controller) {
        this.controller = (UserController) controller;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }



    public static Set<Role> getRoles(HttpServletRequest request, int staff_id) {
        UserController controller = (UserController) request.getServletContext()
                .getAttribute(CONTROLLER_XML_ID);
        return controller.getRoles(staff_id);
    }

}
