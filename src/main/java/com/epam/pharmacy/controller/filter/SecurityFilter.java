package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter checking user access rights
 *
 * @author Yauheni Tsitou
 */
@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        ActionCommand command = ActionFactory.defineCommand(request);
        CommandAccessLevel annotation = command.getClass().getAnnotation(CommandAccessLevel.class);
        if (annotation == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (user == null) {
            String page = PagePath.ERROR_403_PAGE;
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(servletRequest, servletResponse);
            return;
        }
        User.Role userRole = user.getRole();
        User.Role[] accessRoles = annotation.value();
        for (User.Role accessRole : accessRoles) {
            if (accessRole == (userRole)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
