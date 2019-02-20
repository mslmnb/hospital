package com.epam.hospital.action;

import com.epam.hospital.service.LangService;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ActionUtil.GET_ALL;
import static com.epam.hospital.util.ActionUtil.getJsonString;
import static com.epam.hospital.util.ActionUtil.getJsonViewForDefaultDirection;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

public class LangAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(LangAction.class);

    private static final String URI = "lang";

    private LangService service;

    public LangAction(LangService service) {
        super(URI);
        this.service = service;
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
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonViewForDefaultDirection(response, LOG, direction);
                break;
        }
        return result;

    }
}
