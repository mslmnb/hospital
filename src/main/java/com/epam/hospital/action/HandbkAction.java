package com.epam.hospital.action;

import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.service.HandbkItemService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.filter.LangFilter.LANG_ATTRIBUTE_NAME;
import static com.epam.hospital.service.HandbkItemServiceImpl.*;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class HandbkAction extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(HandbkAction.class);

    private static final String URI = "handbk";
    private static final String JSP_FILE_NAME = "/jsp/handbk.jsp";

    private HandbkItemService service;

    public HandbkAction(HandbkItemService service) {
        super(URI);
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);
        String lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);
        String handbkTypeAsString = request.getParameter(TYPE_PARAMETER);
        HandbkType handbkType = null;
        if (handbkTypeAsString!=null&&!handbkTypeAsString.isEmpty()) {
            handbkType = HandbkType.valueOf(handbkTypeAsString.toUpperCase());
        }
        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            case GET_ALL_ITEM_TRANSLATIONS:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAllTranslations(lang, handbkType));
                break;
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(handbkType));
                break;
            case SAVE:
                String idAsString = request.getParameter(ID_PARAMETER);
                String name = request.getParameter(NAME_PARAMETER);
                String typeAsString = request.getParameter(TYPE_PARAMETER);
                result = JSON_VIEW_PREFIX.getPrefix() +
                         getJsonViewForSaveDirection(response, service, idAsString, name, typeAsString);
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
