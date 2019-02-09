package com.epam.hospital.action;

import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.service.TranslationService;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.CheckResult;
import com.epam.hospital.util.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.filter.LangFilter.LANG_ATTRIBUTE_NAME;
import static com.epam.hospital.service.TranslationService.*;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_TRANSLATION_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;
import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class TranslationAction extends AbstractActionWithService{
    private static final Logger LOG = Logger.getLogger(HandbkAction.class);

    private static final String URI = "translation";
    private static final String JSP_FILE_NAME = "/jsp/translation.jsp";

    private TranslationService service;

    public TranslationAction() {
        super(URI, CONTEXT_PARAMETER_FOR_TRANSLATION_SERVICE);
    }

    private void setService(Object service) {
        this.service = (TranslationService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);
        String lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);
        String handbkTypeAsString = request.getParameter("handbk");
        HandbkType handbkType = null;
        if (handbkTypeAsString!=null&&!handbkTypeAsString.isEmpty()) {
            handbkType = HandbkType.valueOf(handbkTypeAsString.toUpperCase());
        }
        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL:
                String handbkItemIdAsString = request.getParameter(HANDBK_ITEM_ID_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(handbkItemIdAsString));
                break;
            case GET:
                try {
                    String idAsString = request.getParameter(ID_PARAMETER);
                    result = service.get(idAsString).getJsonString();
                } catch (AppException e) {
                    response.setStatus(422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            case SAVE:
                String idAsString = request.getParameter(ID_PARAMETER);
                handbkItemIdAsString = request.getParameter(HANDBK_ITEM_ID_PARAMETER);
                String locale = request.getParameter(LOCALE_PARAMETER);
                String translation = request.getParameter(TRANSLATION_PARAMETER);
                try {
                    service.save(idAsString, handbkItemIdAsString, locale, translation);
                    result = "";
                } catch (AppException e) {
                    response.setStatus(422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            case DELETE:
                idAsString = request.getParameter(ID_PARAMETER);
                try {
                    service.delete(idAsString);
                    result = "";
                } catch (AppException e) {
                    response.setStatus(422);
                    result = e.getCheckResult().getJsonString();
                }
                result = JSON_VIEW_PREFIX.getPrefix() + result;
                break;
            default:
                LOG.error("Actions are not defined for direction: " + direction);
                response.setStatus(422);
                result = new CheckResult(UNKNOWN_ERROR).getJsonString();
                break;
        }
        return result;
    }
}
