package com.epam.hospital.web.action;

import com.epam.hospital.web.patient.PatientController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class PatientAction extends AbstractActionWithController {
    private static final String URI = "patients";
    private static final String CONTROLLER_XML_ID = "patientController";

    private static final String JSP_FILE_NAME = "/jsp/patients.jsp";
    private static final String ATTRIBUTE_NAME = "patients";

    private PatientController controller;

    public PatientAction() {
        super(URI, CONTROLLER_XML_ID);
    }

    private void setController(Object controller) {
        this.controller = (PatientController) controller;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (requestURI.startsWith(AJAX_URI_PREFIX)) {
            response.setContentType(JSON_MIMETYPE);
            response.setCharacterEncoding(UTF8_CODE);
            result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(controller.getAllTo());

        } else {
            request.setAttribute(ATTRIBUTE_NAME, controller.getAllTo());
            result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
        }
        return result;
    }

}
