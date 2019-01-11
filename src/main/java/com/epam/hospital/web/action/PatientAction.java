package com.epam.hospital.web.action;

import com.epam.hospital.web.action.Action;
import com.epam.hospital.web.patient.PatientController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER;
import static com.epam.hospital.util.JsonUtil.getJsonString;
import static com.epam.hospital.util.JsonUtil.JSON_MIMETYPE;
import static com.epam.hospital.util.JsonUtil.AJAX_URI_PREFIX;
import static com.epam.hospital.util.JsonUtil.UTF8_CODE;

public class PatientAction extends AbstractAction {
    public static final String ATTRIBUTE_NAME = "patients";
    public static final String URI = "patients";
    public static final String JSP_FILE = "patients.jsp";

    public PatientAction() {
        super(ATTRIBUTE_NAME, URI, JSP_FILE);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        PatientController controller = (PatientController) request.getServletContext()
                                                                  .getAttribute(CONTEXT_PARAMETER_FOR_PATIENT_CONTROLLER);

        if (requestURI.startsWith(AJAX_URI_PREFIX)) {
            response.setContentType(JSON_MIMETYPE);
            response.setCharacterEncoding(UTF8_CODE);
            result  = getJsonString(controller.getAllTo());

        } else {
            request.setAttribute(getAttributeName(), controller.getAllTo());
            result = getJspFile();
        }
        return result;
    }

}
