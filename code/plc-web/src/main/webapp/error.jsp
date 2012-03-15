<%@ page language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title><fmt:message key="errorPage.title" /></title>
    </head>
    <body id="error">
        <div class="errorpage-content">
            <div>
                <h2><fmt:message key="errorPage.heading"/></h2>
                <% 
                    Exception globalException = (Exception) session.getAttribute("globalException");
                    Boolean showStackTrace = (Boolean) session.getAttribute("showStackTrace");
                    if (globalException != null && showStackTrace != null && showStackTrace) { %>
                    <div class="errorpage-message"><fmt:message key="errorPage.stackTrace.message"/></div>
                    <div class="errorpage-exception-box"><% globalException.printStackTrace(new java.io.PrintWriter(out)); %></div>
                    
                <% } else if (request.getAttribute("javax.servlet.error.exception") != null && showStackTrace != null && showStackTrace) { %>
                
                    <div class="errorpage-message"><fmt:message key="errorPage.stackTrace.message"/></div>
                    <div class="errorpage-exception-box"><% ((Exception)request.getAttribute("javax.servlet.error.exception"))
                                           .printStackTrace(new java.io.PrintWriter(out)); %></div>

                <% } else { %>
                
                    <div class="errorpage-message"><fmt:message key="errorPage.noStackTrace.content"/></div>
                    
                <%  } %>
            </div>
        </div>
    </body>
</html>
