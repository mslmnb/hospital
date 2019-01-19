package com.epam.hospital.web;

import com.epam.hospital.model.Role;
import com.epam.hospital.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class SecurityFilter implements Filter {
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
        User user = (User) request.getSession().getAttribute("autorizedUser"); // получить из сессии User

        HttpServletRequest wrapRequest = request;

        if (user != null) { //если авториз user в сессии есть, то обернуть запрос
            // User Name
            String login = user.getLogin();

            // Roles
            Set<Role> roles = user.getRoles();

            // Wrap old request by a new Request with userName and Roles information.
            //wrapRequest = new UserRoleRequestWrapper(login, roles, request);
        }

        // Pages must be signed in.
        if (false){//SecurityUtils.isSecurityPage(request)) {      // требует ли запрос security

//            // If the user is not logged in,
//            // Redirect to the login page.
//            if (user == null) {   // если юзер не авторизован
//
//                String requestUri = request.getRequestURI();
//
//                // Store the current page to redirect to after successful login.
//                int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
//
//                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
//                return;
//            }
//
//            // Check if the user has a valid role?
//            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest); // если авториз юзер не имеет доступа
//            if (!hasPermission) {
//
//                RequestDispatcher dispatcher //
//                        = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");
//
//                dispatcher.forward(request, response);
//                return;
//            }
        }

        chain.doFilter(wrapRequest, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
