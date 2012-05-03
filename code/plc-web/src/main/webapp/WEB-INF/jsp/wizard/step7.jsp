<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="affirm_form">
    <s:form class="form-stacked" id="consentForm" name="affirm" namespace="/www/wizard" action="step8" method="post">
        <fieldset>
            <div class="clearfix">
                <label id="affirmCheckboxes">Tell us that you understand this informed consent process.</label>
                <div class="input">
                    <ul class="inputs-list">
                        <li><label> <s:checkbox cssClass="required" name="affirmUncertainty"/> <span>I have read about <a
                                    title="Click for more info" data-toggle="modal" data-target="#uncertainty-and-risk-modal"
                                    data-backdrop="true" data-keyboard="true">the uncertainty and risk of this research.</a>.
                                    <s:fielderror fieldName="affirmUncertainty" cssClass="error-message"/>
                            </span>
                        </label></li>
                        <li><label> <s:checkbox cssClass="required" name="affirmConsent"/> <span>I provide <a
                                    title="Click for more info" data-toggle="modal" data-target="#providing-consent-modal"
                                    data-backdrop="true" data-keyboard="true">consent</a> for my data to be used in
                                    research.
                                    <s:fielderror fieldName="affirmConsent" cssClass="error-message"/>
                            </span>
                        </label></li>
                        <li><label> <s:checkbox class="required" name="affirmWithdrawlPolicy"/> <span>I understand that although I can <a
                                    title="Click for more info" data-toggle="modal" data-target="#withdrawing-modal"
                                    data-backdrop="true" data-keyboard="true">withdraw</a> at any time, I cannot
                                    withdraw data that has <a title="Click for more info" data-toggle="modal"
                                    data-target="#withdrawing-data-modal" data-backdrop="true"
                                    data-keyboard="true">already been distributed</a>.
                                    <s:fielderror fieldName="affirmWithdrawlPolicy" cssClass="error-message"/>
                            </span>
                        </label></li>
                    </ul>
                    <span class="help-block"> All boxes must be checked to create informed consent. </span>
                </div>
                <div class="actions">
                    <input type="submit" class="btn primary" value="I want to give consent">
                </div>
            </div>
        </fieldset>
    </s:form>
    <div id="uncertainty-and-risk-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Uncertainty and Risk</h3>
        </div>
        <div class="modal-body">
            <p>The risks of public disclosure of your genetic and trait data, including your DNA sequence data, or
                other information you provide, could affect the employment, insurance and financial well-being or social
                interactions of you and your immediate family. The video you just watched, and the consent form, go
                through a long, but by necessity non-comprehensive, list of those risks. It's not comprehensive because
                we simply cannot imagine all the possible risks - they will change over time, and we do not have an
                infinite imagination. You should be prepared for these risks, and be willing to balance them against the
                benefit to scientific research of sharing. If you're not, please do not move forward to the next phase
                of the process.The Personal Genome Project perhaps summarizes the risks most succintly: in principle,
                someone could use information from your health and genomic backgrounds to infer paternity or other
                features of your family history, make claims that could affect your employment, insurance, or other
                financial services, incriminate relatives, make and place synthetic DNA as evidence, or reveal
                propensity for a disease that has no treatment. Please read carefully into Article VI of the consent
                form to see the detailed list provided there.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="providing-consent-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Providing Consent</h3>
        </div>
        <div class="modal-body">
            <p>Consent means that you are providing a legally binding agreement that data you choose to upload will
                be available under Portable Legal Consent for distribution under the Common Genomics Research framework.
                Please do not digitally sign the consent form in the next phase if you do not wish to provide consent,
                or if you do not understand the ideas you have encountered in the process so far.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="withdrawing-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Withdrawing</h3>
        </div>
        <div class="modal-body">
            <p>It is possible to withdraw once you have provided consent. You would need to either contact the
                Common Genomics Research entity to which you uploaded your data and ask them to delete your data and
                notify those who have harvested it to delete your data, or to contact the harvesters of the data
                directly and ask them to delete your data. If you are contacting the harvesters, remember that they do
                not have your email or name, so you'll need to provide your Sage Bionetworks Common Identifier for them
                to know which record is yours and delete it. However, withdrawal and deletion of data may be of limited
                impact if that data has already been distributed to other parties under the Common Genomics Research
                framework.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="withdrawing-data-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Withdrawing Data</h3>
        </div>
        <div class="modal-body">
            <p>Once any data or information about you is posted to the CGR public database, other organizations and
                individuals may acquire copies of it. There will be no way to ensure that they will delete their copies
                of your data or information, or for Sage Bionetworks Common Genomic Research Project or the CGR entity
                who runs an instance of the Consent framework to even know what copies of your data or information may
                exist. Both Sage Bionetworks Common Genomic Research Project and the CGR entity who runs an instance of
                the Consent framework will encourage researchers and other users to use the most current version of the
                CGR public database, but your data may be combined by Sage Bionetworks Common Genomic Research Project
                and/or its collaborators or other third parties in ways that will make it impossible to delete them from
                such datasets.</p>
        </div>
        <div class="modal-footer"></div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#acknowledgeUnderstandingStep');
    });
</script>