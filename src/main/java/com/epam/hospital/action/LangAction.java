package com.epam.hospital.action;

import com.epam.hospital.service.LangService;
import com.epam.hospital.util.ActionUtil;
import com.epam.hospital.util.CheckResult;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.servlet.AppServletContextListner.CONTEXT_PARAMETER_FOR_LANG_SERVICE;
import static com.epam.hospital.util.ActionUtil.GET_ALL;
import static com.epam.hospital.util.ActionUtil.getJsonString;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;
import static com.epam.hospital.util.exception.AppException.UNKNOWN_ERROR;

public class LangAction extends AbstractActionWithService {
    private static final Logger LOG = Logger.getLogger(LangAction.class);

    private static final String URI = "lang";

    private LangService service;

    public LangAction() {
        super(URI, CONTEXT_PARAMETER_FOR_LANG_SERVICE);
    }

    private void setService(Object service) {
        this.service = (LangService) service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result;
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);

        switch (direction) {
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(service.getAll());
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
