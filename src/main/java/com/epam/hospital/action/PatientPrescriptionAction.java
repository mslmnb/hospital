package com.epam.hospital.action;

import com.epam.hospital.service.PatientPrescriptionService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.filter.LangFilter.LANG_ATTRIBUTE_NAME;
import static com.epam.hospital.service.PatientPrescriptionServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.*;

/**
 * The class of actions for work with the information about patient's prescriptions
 * for medical office
 */

public class PatientPrescriptionAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(PatientPrescriptionAction.class);

    private static final String URI = "prescription";
    private static final String JSP_FILE_NAME = "/jsp/prescription.jsp";

    private final PatientPrescriptionService service;

    public PatientPrescriptionAction(PatientPrescriptionService service) {
        super(URI);
        this.service = service;
    }

    /**
     * Describes actions for work with the information about patient's prescriptions
     * for medical office
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        String direction = getDirection(request.getPathInfo(), URI);
        String lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);

        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL:
                String patientIdAsString = request.getParameter(ID_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(patientIdAsString, lang));
                break;
            case SAVE:
                patientIdAsString = request.getParameter(PATIENT_ID_PARAMETER);
                String idAsString = request.getParameter(ID_PARAMETER);
                String applicationDateAsString = request.getParameter(APPLICATION_DATE_PARAMETER);
                String typeIdAsString = request.getParameter(TYPE_ID_PARAMETER);
                String description = request.getParameter(DESCRIPTION_PARAMETER);
                String executionDateAsString = request.getParameter(EXECUTON_DATE_PARAMETER);
                String resultParameter = request.getParameter(RESULT_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() +
                         getJsonViewForSaveDirection(response, service, idAsString,
                                                     patientIdAsString, applicationDateAsString, typeIdAsString,
                                                     description, executionDateAsString, resultParameter);
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
