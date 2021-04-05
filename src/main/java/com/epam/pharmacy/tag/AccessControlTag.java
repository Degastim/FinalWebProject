package com.epam.pharmacy.tag;

import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;


public class AccessControlTag extends TagSupport {
    private String accessRole;

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user != null) {
            User.Role userRole = user.getRole();
            if ((User.Role.valueOf(accessRole.toUpperCase())) == userRole) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }
}
