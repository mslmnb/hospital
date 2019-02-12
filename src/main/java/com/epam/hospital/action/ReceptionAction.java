package com.epam.hospital.action;

import com.epam.hospital.service.PatientService;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.PatientService.*;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_PATIENT_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.*;

public class ReceptionAction extends AbstractActionWithService {
    private static final Logger LOG = Logger.getLogger(ReceptionAction.class);

    private static final String URI = "reception";
    private static final String JSP_FILE_NAME = "/jsp/reception.jsp";

    private PatientService service;

    public ReceptionAction() {
        super(URI, CONTEXT_PARAMETER_FOR_PATIENT_SERVICE);
    }

    private void setService(Object service) {
        this.service = (PatientService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = getDirection(request.getPathInfo(), URI);

        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll());
                break;
            case SAVE:
                String idAsString = request.getParameter(ID_PARAMETER);
                String name = request.getParameter(NAME_PARAMETER);
                String additionalName = request.getParameter(ADDITIONAL_NAME_PARAMETER);
                String surname = request.getParameter(SURNAME_PARAMETER);
                String phone = request.getParameter(PHONE_PARAMETER);
                String email = request.getParameter(EMAIL_PARAMETER);
                String birthdayAsString = request.getParameter(BITHDAY_PARAMETER);
                String admissionDateAsString = request.getParameter(ADMISSION_DATE_PARAMETER);
                try {
                    service.save(idAsString, name, additionalName, surname,
                                 birthdayAsString, phone, email, admissionDateAsString);
                    result = "";
                } catch (AppException e) {
                    response.setStatus(422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            case GET:
                result = JSON_VIEW_PREFIX.getPrefix() +
                        getJsonViewForGetDirection(request, response, service, ID_PARAMETER);
                break;
            case DELETE:
                result = JSON_VIEW_PREFIX.getPrefix() +
                        getJsonViewForDeleteDirection(request, response, service, ID_PARAMETER);
                break;
            default:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonViewForDefaultDirection(response, LOG, direction);
                break;
        }
        return result;

    }
}
