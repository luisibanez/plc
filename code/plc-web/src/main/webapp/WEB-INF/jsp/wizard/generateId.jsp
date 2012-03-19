<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="register_form">
    <h3>Generate Sage Commons User ID</h3>
    <p>The data we collect here will be used to generate a unique ID that you can use to access your profile.  
    You will provide this ID when you return to update your data files, change profile settings, or withdraw from the study.</p>
    <s:form cssClass="form-stacked" id="registerForm" name="register" namespace="/www/wizard" action="generateId" method="post">
        <fieldset>
            <div class="clearfix">
                <label for="firstName">First Name</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="firstName" name="patientDemographics.firstName" size="30" cssErrorClass="error-field" theme="simple"/>
                    <s:fielderror fieldName="patientDemographics.firstName" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="birthName">Last Name at Birth</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="birthName" name="patientDemographics.birthName" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="patientDemographics.birthName" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="birthPlace">Birth City</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required" id="birthPlace" name="patientDemographics.birthPlace" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="patientDemographics.birthPlace" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="birthCountry">Birth Country</label>
                <div class="input">
                    <s:select id="birthCountry" cssClass="xlarge required" name="patientDemographics.birthCountry"
                        list="%{@com.fiveamsolutions.plc.data.enums.Country@values()}" listValue="code"
                        headerKey="" headerValue="%{getText('select.emptyOption')}"        
                        cssErrorClass="error-field"/>
                    <s:fielderror fieldName="patientDemographics.birthCountry" cssClass="error-message"/>
                </div>
            </div>
            <div class="clearfix">
                <label for="dob">Date of Birth</label>
                <div class="input">
                    <s:textfield cssClass="xlarge required date" id="dob" name="patientDemographics.birthDate" size="30" cssErrorClass="error-field"/>
                    <s:fielderror fieldName="patientDemographics.birthDate" cssClass="error-message"/>
                </div>
            </div>
            <div class="actions">
                <s:submit cssClass="btn primary" value="Generate ID"/>
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
        markProgress('#generateIdStep');
    });
</script>