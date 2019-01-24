package com.epam.hospital.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    String execute(HttpServletRequest request, HttpServletResponse response);

    String getUri();

}
