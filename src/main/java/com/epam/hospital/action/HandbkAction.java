package com.epam.hospital.action;

import com.epam.hospital.model.handbk.HandbkType;
import com.epam.hospital.service.HandbkService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.filter.LangFilter.LANG_ATTRIBUTE_NAME;
import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_HANDBK_SERVICE;
import static com.epam.hospital.util.ActionUtil.*;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class HandbkAction extends AbstractActionWithService{

    private static final Logger LOG = Logger.getLogger(StaffAction.class);

    private static final String URI = "handbk";

    private HandbkService service;

    public HandbkAction() {
        super(URI, CONTEXT_PARAMETER_FOR_HANDBK_SERVICE);
    }

    private void setService(Object service) {
        this.service = (HandbkService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);
        String lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE_NAME);

        switch (direction) {
//            case FORWARD_TO_JSP:
//                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
//                break;
            case GET_ALL:
                HandbkType handbkType = HandbkType.valueOf(request.getParameter("handbk").toUpperCase());
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll(lang, handbkType));
                break;
//            case SAVE:
//                result = JSON_VIEW_PREFIX.getPrefix(); //+ result;
//                break;
            default:
                LOG.error("Actions are not defined for direction: " + direction);
                //result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                result = "";
                break;
        }
        return result;
    }
}
