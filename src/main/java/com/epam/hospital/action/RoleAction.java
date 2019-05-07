package com.epam.hospital.action;

import com.epam.hospital.model.Role;
import com.epam.hospital.util.ActionUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static com.epam.hospital.util.ActionUtil.GET_ALL;
import static com.epam.hospital.util.ActionUtil.getJsonString;
import static com.epam.hospital.util.ViewPrefixType.JSON_VIEW_PREFIX;

/**
 * The class of actions for work with the information about the roles of the system
 */

public class RoleAction extends AbstractAction{
    private static final Logger LOG = Logger.getLogger(RoleAction.class);

    private static final String URI = "role";

    public RoleAction() {
        super(URI);
    }

    /**
     * Describes actions for work with the information about the roles of the system
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        String direction = ActionUtil.getDirection(request.getPathInfo(), URI);
        switch (direction) {
            case GET_ALL:
                result = JSON_VIEW_PREFIX.getPrefix() + getJsonString(Arrays.asList(Role.values()));
                break;
            default:
                ActionUtil.logAndThrowForIndefiniteActionException(request, LOG);
                break;
        }
        return result;
    }
}
