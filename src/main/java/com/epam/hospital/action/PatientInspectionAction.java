package com.epam.hospital.action;

import com.epam.hospital.service.PatientInspectionService;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.PatientInspectionService.*;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_PATIENT_INSPECTION_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.*;

public class PatientInspectionAction extends AbstractActionWithService {
    private static final Logger LOG = Logger.getLogger(PatientInspectionAction.class);

    private static final String URI = "inspection";
    private static final String JSP_FILE_NAME = "/jsp/inspection.jsp";

    private PatientInspectionService service;

    public PatientInspectionAction() {
        super(URI, CONTEXT_PARAMETER_FOR_PATIENT_INSPECTION_SERVICE);
    }

    private void setService(Object service) {
        this.service = (PatientInspectionService) service;
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
                String patientIdAsString = request.getParameter(ID_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(patientIdAsString));
                break;
            case SAVE:
                patientIdAsString = request.getParameter(PATIENT_ID_PARAMETER);
                String idAsString = request.getParameter(ID_PARAMETER);
                String dateAsString = request.getParameter(DATE_PARAMETER);
                String inspection = request.getParameter(INSPECTION_PARAMETER);
                String complaints = request.getParameter(COMPLAINTS_PARAMETER);
                try {
                    service.save(idAsString, patientIdAsString, dateAsString, inspection, complaints);
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
