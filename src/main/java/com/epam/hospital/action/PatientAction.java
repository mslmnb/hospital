package com.epam.hospital.action;

import com.epam.hospital.service.PatientService;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.PatientServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

/**
 * The class of actions for work with the information about the patients
 * for medical office
 */

public class PatientAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(PatientAction.class);

    private static final String URI = "patients";
    private static final String JSP_FILE_NAME = "/jsp/patients.jsp";

    private final PatientService service;

    public PatientAction(PatientService service) {
        super(URI);
        this.service = service;
    }

    /**
     * Describes actions for work with the information about the patients
     * for medical office
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
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
                String primaryInspection = request.getParameter(PRIMARY_INSPECTION_PARAMETER);
                String primaryComplaints = request.getParameter(PRIMARY_COMPLAINTS_PARAMETER);
                String primaryDiagnssIdAsString = request.getParameter(PRIMARY_DIAGNOSIS_ID_PARAMETER);
                String finalDiagnssIdAsString = request.getParameter(FINAL_DIAGNOSIS_ID_PARAMETER);
                String dischargeDateAsString = request.getParameter(DISCHARGE_DATE_PARAMETER);

                try {
                    service.updatePrimaryExamAndDischarge(idAsString, primaryInspection, primaryComplaints,
                                                          primaryDiagnssIdAsString, finalDiagnssIdAsString,
                                                          dischargeDateAsString);
                    result = "";
                } catch (AppException e) {
                    response.setStatus(RESPONSE_STATUS_422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;

            case GET:
                result = JSON_VIEW_PREFIX.getPrefix() +
                        getJsonViewForGetDirection(request, response, service, ID_PARAMETER);
                break;
            default:
                ActionUtil.logAndThrowForIndefiniteActionException(request, LOG);
                break;
        }
        return result;
    }

}
