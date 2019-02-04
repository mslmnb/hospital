package com.epam.hospital.action;

import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.hospital.util.ActionUtil.FORWARD_TO_JSP;
import static com.epam.hospital.util.ViewPrefixType.FORWARD_VIEW_PREFIX;

public class AdminAction extends AbstractAction {
    private static final Logger LOG = Logger.getLogger(AdminAction.class);

    private static final String URI = "admin";
    private static final String JSP_FILE_NAME = "/jsp/admin.jsp";

    public AdminAction() {
        super(URI);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);
        String result;

        switch (direction) {
            case FORWARD_TO_JSP:
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
            default:
                LOG.error("Actions are not defined for direction: " + direction);
                result = FORWARD_VIEW_PREFIX.getPrefix() + JSP_FILE_NAME;
                break;
        }
        return result;
    }
}
