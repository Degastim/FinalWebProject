package com.epam.pharmacy.tag;

import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * A tag that checks if the element can be displayed on the page to the user.
 *
 * @author Yauheni Tsitou.
 */
public class AccessControlTag extends TagSupport {
    /**
     * Contains the role of the user who is allowed access
     */
    private String accessRole;

    /**
     * Sets the role that is denied access
     *
     * @param accessRole contains the role of the user who is allowed access
     */
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
