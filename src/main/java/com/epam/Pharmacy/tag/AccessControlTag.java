package com.epam.Pharmacy.tag;

import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.model.entity.User;

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
            User.UserRole userRole = user.getRole();
            if ((User.UserRole.valueOf(accessRole.toUpperCase())) == userRole) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }
}
