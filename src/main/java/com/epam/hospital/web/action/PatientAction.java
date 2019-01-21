package com.epam.hospital.web.action;

import com.epam.hospital.web.patient.PatientController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;
import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER;

public class PatientAction extends AbstractAction {
    public static final String URI = "patients";

    public static final String JSP_FILE_NAME = "/jsp/patients.jsp";
    public static final String ATTRIBUTE_NAME = "patients";

    public PatientAction() {
        super(URI);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        PatientController controller = (PatientController) request.getServletContext()
                .getAttribute(CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER);

        if (controller == null) {
            // logger
            throw new IllegalStateException("PatientController is not defined.");
        }
        if (controller.connectionPoolIsNull()) {
            // logger
            throw new IllegalStateException("ConnectionPool is not defined.");
        }

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
