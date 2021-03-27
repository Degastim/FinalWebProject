package com.epam.Pharmacy.tag;

import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.resource.MessageManager;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class DrugTableTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        JspWriter out = pageContext.getOut();
        try {
            List<Drug> drugList = (List<Drug>) pageContext.getRequest().getAttribute(RequestParameter.DRUG_LIST);
            out.write("<form method=\"get\" action=\"controller\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"redirect_to_edit_drug\">");
            out.write("<table class=\"table table-dark table-hover table-bordered\"><tr>");
            out.write("<td>" + MessageManager.getMessage("drugTable.drugNameColumn", locale) + "</td>");
            out.write("<td>" + MessageManager.getMessage("drugTable.drugAmountColumn", locale) + "</td>");
            out.write("<td>" + MessageManager.getMessage("drugTable.drugDescriptionColumn", locale) + "</td>");
            out.write("<td>" + MessageManager.getMessage("drugTable.drugDosageColumn", locale) + "</td>");
            out.write("<td>" + MessageManager.getMessage("drugTable.drugPriceColumn", locale) + "</td>");
            out.write("<td>" + MessageManager.getMessage("drugTable.drugEditColumn", locale) + "</td>");
            for (Drug drug : drugList) {
                out.write("<tr>");
                out.write("<td>" + drug.getDrugName() + "</td>");
                out.write("<td>" + drug.getAmount() + "</td>");
                out.write("<td>" + drug.getDescription() + "</td>");
                out.write("<td>" + drug.getDosage() + "</td>");
                out.write("<td>" + drug.getPrice() + "</td>");
                out.write("<td><button class=\"btn btn-info\" type=\"submit\" name=\"drugId\" value=\"" + drug.getDrugId() + "\">" + MessageManager.getMessage("drugTable.edit", locale) + "</button></td>");
                out.write("</tr>");
            }
            out.write("</table>");
            out.write("</form>");
            out.write("<form method=\"get\" action=\"controller\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"redirect_to_add_drug\">");
            out.write("<button class=\"btn btn-success w-100\" type=\"submit\">"+ MessageManager.getMessage("drugTable.add", locale) +  "</button>");
            out.write("</form>");
        } catch (IOException e) {
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }
}