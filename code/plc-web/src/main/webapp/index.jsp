<%
if (request.getRemoteUser() == null) {
    response.sendRedirect(request.getContextPath() + "/www/wizard/start.action");
} else {
    response.sendRedirect(request.getContextPath() + "/www/protected/uploadData/view.action");
}
%>
