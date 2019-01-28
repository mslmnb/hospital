package com.epam.hospital.action.patient;

import com.epam.hospital.action.AbstractActionWithService;
import com.epam.hospital.model.Patient;
import com.epam.hospital.service.PatientService;
import com.epam.hospital.util.CheckResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ValidationUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_PATIENT_SERVICE;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class PatientAction extends AbstractActionWithService {
    private static final String URI = "patients";

    private static final String JSP_FILE_NAME = "/jsp/patients.jsp";
    private static final String ATTRIBUTE_NAME = "patients";

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

        String method = request.getMethod();

        switch (request.getMethod()) {
            case "GET":
                response.setStatus(SC_OK);
                String requestURI = request.getRequestURI().substring(request.getContextPath().length());
                if (requestURI.startsWith(AJAX_URI_PREFIX)) {
                    response.setContentType(JSON_MIMETYPE);
                    result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll());
                } else {
                    result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;  // сразу обрабатывать в сервлете
                }
                break;
            case "POST":
                String idAsString = request.getParameter("id");
                Integer id = idAsString.isEmpty() ? null : Integer.parseInt(idAsString);
                String name = request.getParameter("name");
                String additionalName = request.getParameter("additional_name");
                String surname = request.getParameter("surname");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                CheckResult checkResult = new CheckResult("ValidationException");
                checkNotEmpty(name, "Name", checkResult);
                checkNotEmpty(surname, "Surname", checkResult);
                LocalDate birthday = checkAndReturnDate(request.getParameter("birthday"), checkResult);
                checkPhone(phone, checkResult);
                checkEmail(email, checkResult);
                if (!checkResult.foundErrors()) {
                    Patient patient = new Patient(id, name, additionalName, surname,
                            birthday, phone, email);
                    if (patient.isNew()) {
                        service.create(patient);
                        result = "";
                    } else {
                        // отправить сообщение о неверном параметре id
                        result = "";
                    }
                } else {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(422);
                    result =  checkResult.getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            default:
                result = "";
                break;
        }

        return result;
    }

}
