package com.epam.hospital.action;

import com.epam.hospital.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAction extends AbstractActionWithService {
    private static final String URI = "users";
    private static final String CONTROLLER_XML_ID = "userController";

    private UserService service;

    public UserAction() {
        super(URI, CONTROLLER_XML_ID);
    }

    private void setService(Object service) {
        this.service = (UserService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
