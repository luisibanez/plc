<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="sage_form">
    <h3>Sign in using your Portal Legal Consent Username</h3>
    <form action="j_security_check" method="post" id="loginForm" name="loginForm" onsubmit="${onSubmit} return false;">
        <h2>Sign in</h2>
        <br />
        <c:if test="${not empty param.failedLogin}">
            <div id="error-box" class="error-box">
                <fmt:message key="errors.password.mismatch" />
            </div>
        </c:if>
        <div class="field-text">
            <label for="username"><fmt:message key="login.username" /></label><br> 
            <input type="text" tabindex="1" id="username" name="j_username" maxlength="50" size="25" value="" />
            <fmt:message key="login.forgotUsername.href" var="forgotUsernameHref" />
        </div>
        <div class="field-text">
            <label for="login"><fmt:message key="login.password" /></label><br> 
            <input type="password" tabindex="2" id="password" name="j_password" maxlength="50" size="25" value="" />
        </div>
        <fmt:message key="button.signIn" var="signInLabel" />
        <input id="signInButton" class="button primary-button" type="button" value="${signInLabel}" tabindex="6" onclick="submit(); return false;" />
    </form>
</div>