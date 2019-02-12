package com.epam.hospital.filter;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.epam.hospital.servlet.AppServletContextListner.SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER;

public class SecurityFilter implements Filter {
    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";

    private static final Map<String, Set<Role>> accessMap = new HashMap<>();
    static {
        Set<Role> doctorAndNurseRoles = new HashSet<>();
        doctorAndNurseRoles.add(Role.ROLE_DOCTOR);
        doctorAndNurseRoles.add(Role.ROLE_NURSE);
        Set<Role> nurseRole = new HashSet<>();
        nurseRole.add(Role.ROLE_NURSE);
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(Role.ROLE_ADMIN);
        accessMap.put("/admin", adminRole);
        accessMap.put("/staff", adminRole);
        accessMap.put("/users", adminRole);
        accessMap.put("/handbk", adminRole);
        accessMap.put("/translation", adminRole);
        accessMap.put("/patients", doctorAndNurseRoles);
        accessMap.put("/reception", nurseRole);

    }

    public void destroy() {
    }

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
                        throw new IllegalAccessError();
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
