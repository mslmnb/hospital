package com.epam.hospital.filter;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.util.exception.IllegalAccessException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import static com.epam.hospital.action.LoginAction.SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER;

/**
 * The filter checks access rights of the authorized user to a resource
 */

public class SecurityFilter implements Filter {
    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";

    /**
     * The map where a key - a resource,
     *               a value - a user's role which has access to this resource
     */
    private static final Map<String, Set<Role>> accessMap = new HashMap<>();
    static {
        Set<Role> doctorAndNurseRoles = new HashSet<>();
        doctorAndNurseRoles.add(Role.ROLE_DOCTOR);
        doctorAndNurseRoles.add(Role.ROLE_NURSE);
        Set<Role> nurseRole = new HashSet<>();
        nurseRole.add(Role.ROLE_NURSE);
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(Role.ROLE_ADMIN);
        Set<Role> doctorRole = new HashSet<>();
        doctorRole.add(Role.ROLE_DOCTOR);
        accessMap.put("/admin", adminRole);
        accessMap.put("/staff", adminRole);
        accessMap.put("/users", adminRole);
        accessMap.put("/role", adminRole);
        accessMap.put("/handbk", adminRole);
        accessMap.put("/translation", adminRole);
        accessMap.put("/reception", nurseRole);
        accessMap.put("/inspection", doctorRole);
        accessMap.put("/diagnosis", doctorRole);
        accessMap.put("/patients", doctorAndNurseRoles);
        accessMap.put("/prescription", doctorAndNurseRoles);
        accessMap.put("/noAdmin", doctorAndNurseRoles);
    }

    /**
     * Checks access rights of the authorized user to a resource
     * @param servletRequest the {@code ServletRequest} object contains the client's request
     * @param servletResponse the {@code ServletResponse} object contains the filter's response
     * @param chain the {@code FilterChain} for invoking the next filter or the resource
     * @throws IOException if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getPathInfo().equals(LOGIN_PATH)) {
            chain.doFilter(request, response);
        } else {
            User authorizedUser = (User) request.getSession().getAttribute(SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER);

            if (accessMap.containsKey(request.getPathInfo())) {   // request needs protection
                if (authorizedUser == null) {                   // user isn't authorized
                    request.getRequestDispatcher(ROOT_PATH).forward(request, response);
                } else {
                    boolean hasAccess = accessMap.get(request.getPathInfo()).contains(authorizedUser.getRole());
                    if (hasAccess) {
                        chain.doFilter(request, response);
                    } else {
                        throw new IllegalAccessException("Illegal access to the resource.");
                    }
                }
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
