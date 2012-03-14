<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="participation_form">
    <s:form cssClass="form-stacked" id="consentForm" namespace="/www/wizard" action="step3" method="post">
        <fieldset>
            <div class="clearfix">
                <label id="participationCheckboxes">Are you willing to participate in common genetic research?</label>
                <div class="input">
                    <ul class="inputs-list">
                        <li>
                            <label>
                                <s:checkbox cssClass="required" name="participation"/>
                                <span>Yes, I want to participate in common genomic research</span>
                                <s:fielderror fieldName="participation" cssClass="error-message"/>
                            </label>
                        </li>
                    </ul>
                </div>
                <div class="actions">
                    <input type="submit" class="btn primary" value="Next">
                </div>
            </div>
        </fieldset>
    </s:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#willingnessStep');
    });
</script>

