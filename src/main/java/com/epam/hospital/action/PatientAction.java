package com.epam.hospital.action;

import com.epam.hospital.model.Patient;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.hospital.service.PatientService.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ValidationUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_PATIENT_SERVICE;

public class PatientAction extends AbstractActionWithService {
    private static final Logger LOG = Logger.getLogger(PatientAction.class);

    private static final String URI = "patients";
    private static final String JSP_FILE_NAME = "/jsp/patients.jsp";

    private PatientService service;

    public PatientAction() {
        super(URI, CONTEXT_PARAMETER_FOR_PATIENT_SERVICE);
    }

    private void setService(Object service) {
        this.service = (PatientService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);

        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll());
                break;
            case SAVE:
                String idAsString = request.getParameter(ID_PARAMETER);
                Integer id = idAsString.isEmpty() ? null : Integer.parseInt(idAsString);
                String name = request.getParameter(NAME_PARAMETER);
                String additionalName = request.getParameter(ADDITIONAL_NAME_PARAMETER);
                String surname = request.getParameter(SURNAME_PARAMETER);
                String phone = request.getParameter(PHONE_PARAMETER);
                String email = request.getParameter(EMAIL_PARAMETER);
                String birthdayAsString = request.getParameter(BITHDAY_PARAMETER);
                try {
                    service.save(idAsString, name, additionalName, surname, birthdayAsString, phone, email);
                    result = "";
                } catch (AppException e) {
                    response.setStatus(422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            default:
                LOG.error("Actions are not defined for direction: " + direction);
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
        }
        return result;
    }

}
