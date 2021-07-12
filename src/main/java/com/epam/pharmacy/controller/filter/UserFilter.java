package com.epam.pharmacy.controller.filter;

import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.service.impl.UserServiceImpl;
import com.epam.pharmacy.util.resource.MessageManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter that updates the values of user fields
 *
 * @author Yauheni Tsitou
 */
@WebFilter(urlPatterns = {"/*"})
public class UserFilter implements Filter {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String MESSAGE_KEY_USER_NOT_FIND = "main.errorMessage.noUser";
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE_NO_USER = "userNotFoundError";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(SessionAttribute.USER);
        if (sessionUser == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        long sessionUserId = sessionUser.getId();
        try {
            Optional<User> userDao = userService.findById(sessionUserId);
            if (userDao.isEmpty()) {
                session.removeAttribute(SessionAttribute.USER);
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_USER_NOT_FIND, locale);
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE_NO_USER, errorMessage);
                String page = PagePath.MAIN_PAGE;
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
                dispatcher.forward(servletRequest, servletResponse);
            } else {
                User user = userDao.get();
                session.setAttribute(SessionAttribute.USER, user);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
