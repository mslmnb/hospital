package com.epam.hospital.action;

import com.epam.hospital.service.PatientDiagnosisService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.filter.LangFilter.LANG_ATTRIBUTE_NAME;
import static com.epam.hospital.service.PatientDiagnosisServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

/**
 * The class of actions for work with the information about patient's diagnoses
 * for medical office
 */

public class PatientDiagnosisAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(PatientDiagnosisAction.class);

    private static final String URI = "diagnosis";
    private static final String JSP_FILE_NAME = "/jsp/diagnosis.jsp";

    private final PatientDiagnosisService service;

    public PatientDiagnosisAction(PatientDiagnosisService service) {
        super(URI);
        this.service = service;
    }

    /**
     * Describes actions for work with the information about patient's diagnoses
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
                String dateAsString = request.getParameter(DATE_PARAMETER);
                String diagnosisIdAsString = request.getParameter(DIAGNOSIS_ID_PARAMETER);
                String diagnosisTypeIdAsString = request.getParameter(DIAGNOSIS_TYPE_ITEM_ID_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() +
                         getJsonViewForSaveDirection(response, service, idAsString, patientIdAsString,
                                                     dateAsString, diagnosisIdAsString, diagnosisTypeIdAsString);
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
