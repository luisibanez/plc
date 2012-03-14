<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="rights_form">
    <s:form cssClass="form-stacked" id="consentForm" name="rights" namespace="/www/wizard" action="step5" method="post">
        <fieldset>
            <div class="clearfix">
                <label id="rightsCheckboxes">These are the rights you are granting to 
                    <a title="Click for more info" data-toggle="modal" data-target="#qualified-researchers-modal" data-backdrop="true" data-keyboard="true">qualified researchers</a>
                </label>
                <div class="input">
                    <ul class="inputs-list">
                        <li>
                            <label>
                                <s:checkbox cssClass="required" name="researchPermission"/>
                                <s:fielderror fieldName="researchPermission" cssClass="error-message"/>
                                <span>
                                    Right to <a title="Click for more info" data-toggle="modal" data-target="#right-to-research-modal" data-backdrop="true" data-keyboard="true">do research</a> with my data
                                </span>
                            </label>
                        </li>
                        <li>
                            <label> 
                                <s:checkbox cssClass="required" name="redistributePermission"/>
                                <s:fielderror fieldName="redistributePermission" cssClass="error-message"/>
                                <span>
                                    Right to <a title="Click for more info" data-toggle="modal" data-target="#right-to-redistribute-modal" data-backdrop="true" data-keyboard="true">redistribute</a> my data
                                </span>
                            </label>
                        </li>
                        <li>
                            <label>
                                <s:checkbox cssClass="required" name="publishPermission"/>
                                <s:fielderror fieldName="publishPermission" cssClass="error-message"/>
                                <span>
                                    Right to <a title="Click for more info" data-toggle="modal" data-target="#right-to-publish-modal" data-backdrop="true" data-keyboard="true">publish the results of research</a> from my data
                                </span>
                            </label>
                        </li>
                        <li>
                            <label>
                                <s:checkbox cssClass="required" name="commercializePermission"/>
                                <s:fielderror fieldName="commercializePermission" cssClass="error-message"/>
                                <span>
                                    Right to <a title="Click for more info" data-toggle="modal" data-target="#right-to-commercialize-modal" data-backdrop="true" data-keyboard="true">commercialize products derived from research</a> on my data
                                </span>
                            </label>
                        </li>
                    </ul>
                    <span class="help-block"> All boxes must be checked to move forward in the consent process </span>
                </div>
                <div class="actions">
                    <input type="submit" class="btn primary" value="Next">
                </div>
            </div>
        </fieldset>
    </s:form>

    <div id="qualified-researchers-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Qualified Researchers</h3>
        </div>
        <div class="modal-body">
            <p>Researchers who qualify to access the data you share will have gone through a process to make sure
                they're real people - not the kind of software robots that create spam in your mailbox - and will have
                signed a contract in which they commit not to identify you, not to harm you, and not to give your data
                to anyone else who hasn't signed the same terms. This is a low barrier of qualification, because we want
                a wide variety of researchers using Common Genomic Research data. We'll explain the risks later in this
                process, because it's important you understand them. By setting this barrier low, we increase the number
                of researchers, but we can always raise it if we - and you - decide it's necessary.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="right-to-research-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Right to Research</h3>
        </div>
        <div class="modal-body">
            <p>Research is a broad term. It can mean anything from poking around inside data sets to see what clicks
                to using data in a large-scale, corporate clinical trial. Basically we are defining research as the use
                of your data to figure things out about biology, health, and more. It's about asking questions that are
                driven by data, or using data to formulate new questions. It's also about adding new data - new bits of
                information, what are often called annotations - that adds context to your data and makes it more useful
                in the assembly of data into knowledge. Research does not include the sale of your data as a product
                itself - your data will be free of charge in the CGR system, always, including to you.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="right-to-redistribute-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Right to Redistribute</h3>
        </div>
        <div class="modal-body">
            <p>Data is easy to redistribute once it's in an online database, just like other forms of digital
                content such as music or ebooks. Controls on redistribution are complex, difficult to implement,
                expensive, and often fail to work (or at best, only work on nice people, not those who actively mean to
                get copies no matter what). Thus we are not implementing complex controls on redistribution, and indeed
                we are going to allow redistribution under a certain set of circumstances. A qualified researcher who
                accesses your data will have the right to redistribute it, but only to someone who has signed up to the
                terms of the system: don't re-identify people, don't do harm to people, publish results openly, and in
                turn, don't redistribute to someone who hasn't signed up for the same deal. We won't have the power to
                actively punish those who violate, but we will kick them out of the entire system and name them
                publicly. Violation of the terms will be a professional sin akin to plagiarism, which although not a
                legal crime, is an incredibly powerful control in its own right.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="right-to-publish-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Right to Publish</h3>
        </div>
        <div class="modal-body">
            <p>Most traditional researchers are looking to make discoveries that they can publish in scholarly
                journals. This is a method of knowledge dissemination that is hundreds of years old, and one that is
                deeply enabled by the internet. We're simply making sure that this right to publish is explicit - that
                you know about it, and that researchers know about it. However, there is a twist - all those
                publications have to be made available for you, and the world, to read under 'Open Access' terms. You'll
                learn more about that in the next part of the process.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="right-to-commercialize-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Right to Commercialize</h3>
        </div>
        <div class="modal-body">
            <p>One of the main reasons we're doing this is that we don't have enough products in health care - new
                therapies and medicines, new diagnostics, software tools that help us stay healthy, and more. Our
                explicit goal is to turn Common Genomic Research into the basis for new product discovery. This means
                that you are granting the right to researchers who make discoveries to turn them into products, without
                an expectation that you'll personally profit back from those products. Products are anything that can
                come out of your data: drugs, diagnostics, software apps, hardware, and more. Products are not your data
                itself. Your data is free, and always will be.</p>
        </div>
        <div class="modal-footer"></div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#grantRightsStep');
    });
</script>