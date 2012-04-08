<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="register_form">
    <h3>User Profile</h3>
    <p>Set up your user profile and login credentials:</p>
    <s:form cssClass="form-stacked" id="registerForm" name="register" namespace="/www/wizard" action="register" method="post">
        <fieldset>
            <div class="clearfix">
                <label for="fullName">Full Name</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="fullName" name="user.fullName" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="user.fullName" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="username">Username</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="username" name="user.username" size="20" cssErrorClass="error-field"/>
                    <s:fielderror>
                        <s:param>user.username</s:param>
                        <s:param>user</s:param>
                    </s:fielderror>
                </div>
            </div>
            <div class="clearfix">
                <label for="password">Password</label>
                <div class="input">
                    <s:password cssClass="xlarge required" id="password" name="user.password" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="user.password" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="repeatPassword">Re-enter Password</label>
                <div class="input">
                    <s:password cssClass="xlarge required" id="repeatPassword" name="repeatPassword" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="repeatPassword" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="email">Email</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required email" id="email" name="user.email" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="user.email" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="email">Re-Enter Email</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required email" id="repeatEmail" name="repeatEmail" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="repeatEmail" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="email">Question</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="email" name="challengeQuestion" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="challengeQuestion" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="email">Answer</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="email" name="challengeAnswer" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="challengeAnswer" cssClass="error-message"/>
                </div>
            </div>
            
            <div class="actions">
                <input type="submit" class="btn primary" value="Submit">
            </div>
        </fieldset>
    </s:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#dob').datepicker({
    		changeMonth: true,
    		changeYear: true
    	});
        markProgress('#userProfileStep');
    });
</script>