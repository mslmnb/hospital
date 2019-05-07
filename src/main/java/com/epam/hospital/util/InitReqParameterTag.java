package com.epam.hospital.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
public class InitReqParameterTag extends TagSupport{
    @Override
    public int doStartTag() throws JspException {
        String typeParameter = pageContext.getRequest().getParameter("type");
        String idParameter = pageContext.getRequest().getParameter("id");
        String nameParameter = pageContext.getRequest().getParameter("name");
        String reqParameter = (typeParameter == null) ? "" : "&type=" + typeParameter;
        reqParameter = (idParameter == null) ? reqParameter : reqParameter + "&id=" + idParameter;
        reqParameter = (idParameter == null) ? reqParameter : reqParameter + "&name=" + nameParameter;
        pageContext.setAttribute("reqParameter", reqParameter);
        return SKIP_BODY;
    }
}
