package com.epam.hospital.action;

import com.epam.hospital.service.PatientInspectionService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.PatientInspectionServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.*;

/**
 * The class of actions for work with the information about patient's inspections
 * for medical office
 */

public class PatientInspectionAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(PatientInspectionAction.class);

    private static final String URI = "inspection";
    private static final String JSP_FILE_NAME = "/jsp/inspection.jsp";

    private final PatientInspectionService service;

    public PatientInspectionAction(PatientInspectionService service) {
        super(URI);
        this.service = service;
    }

    /**
     * Describes actions for work with the information about patient's inspections
     * for medical office
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
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
                result = JSON_VIEW_PREFIX.getPrefix() +
                         getJsonViewForSaveDirection(response, service, idAsString,
                                                     patientIdAsString, dateAsString, inspection, complaints);
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
                ActionUtil.logAndThrowForIndefiniteActionException(request, LOG);
                break;
        }
        return result;
    }
}
