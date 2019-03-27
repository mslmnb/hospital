package com.epam.hospital.action;

import com.epam.hospital.service.HandbkItemServiceImpl;
import com.epam.hospital.service.TranslationService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.service.TranslationServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class TranslationAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(HandbkAction.class);

    private static final String URI = "translation";
    private static final String JSP_FILE_NAME = "/jsp/translation.jsp";

    private TranslationService service;

    public TranslationAction(TranslationService service) {
        super(URI);
        this.service = service;
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
                String handbkItemIdAsString = request.getParameter(HandbkItemServiceImpl.ID_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(handbkItemIdAsString));
                break;
            case SAVE:
                String idAsString = request.getParameter(ID_PARAMETER);
                handbkItemIdAsString = request.getParameter(HANDBK_ITEM_ID_PARAMETER);
                String locale = request.getParameter(LOCALE_PARAMETER);
                String translation = request.getParameter(TRANSLATION_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() +
                         getJsonViewForSaveDirection(response, service, idAsString,
                                                     handbkItemIdAsString, locale, translation);
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
