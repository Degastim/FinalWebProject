package com.epam.pharmacy.tag;

import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * A tag that checks if the user is in the session.
 *
 * @author Yauheni Tsitou.
 */
public class UserExistTag extends TagSupport {
    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user != null) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
