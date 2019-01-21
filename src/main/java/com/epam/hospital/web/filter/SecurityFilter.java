package com.epam.hospital.web.filter;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;
import com.epam.hospital.to.AuthorizedUser;
import com.epam.hospital.web.user.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.epam.hospital.web.AppServletContextListner.CONTEXT_PARAMETER_FOR_USER_CONTROLLER;
import static com.epam.hospital.web.AppServletContextListner.SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER;

public class SecurityFilter implements Filter {
    private static final String ROOT_PATH = "/";
    private static final Map<String, Set<Role>> accessMap = new HashMap<>();
    static {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_DOCTOR);
        roles.add(Role.ROLE_NURSE);
        accessMap.put("/patients", roles);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

        if (request.getPathInfo().equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        AuthorizedUser authorizedUser = (AuthorizedUser) request.getSession().getAttribute(SESSION_ATTRIBUTE_FOR_AUTHORIZED_USER); // получить из сессии User

        if (accessMap.containsKey(request.getPathInfo())){   // требует ли запрос security
            if (authorizedUser == null) {   // если юзер не авторизован
                request.getRequestDispatcher(ROOT_PATH).forward(request, response);
            } else {
                boolean hasAccess = false;
                UserController controller = (UserController) request.getServletContext()
                        .getAttribute(CONTEXT_PARAMETER_FOR_USER_CONTROLLER);
                for (Role userRole: controller.getRoles(authorizedUser.getId())) {
                    if (accessMap.get(request.getPathInfo()).contains(userRole)) {
                        hasAccess = true;
                        break;
                    }
                }
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

    public void init(FilterConfig config) throws ServletException {

    }

}
