package com.epam.Pharmacy.tag;

import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class UserExistTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user != null) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
