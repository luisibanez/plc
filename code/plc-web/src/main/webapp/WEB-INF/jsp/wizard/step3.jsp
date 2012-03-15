<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="requests_form">
    <s:form cssClass="form-stacked" namespace="/www/wizard" action="step4" method="post">
        <fieldset>
            <div class="clearfix">
                <label id="conditions">We will impose the following conditions on researchers through <a
                    title="Click for more info" data-toggle="modal" data-target="#terms-of-use-modal" data-backdrop="true"
                    data-keyboard="true">Terms of Use</a>, which either must be visible on the website that hosts your
                    data or must be digitally signed by those who access your data.
                </label>
            </div>
            <div class="clearfix">
                <div class="input">
                    <ul>
                        <li>Do not attempt to <a title="Click for more info" data-toggle="modal"
                            data-target="#re-identification-modal" data-backdrop="true" data-keyboard="true">re-identify
                                me</a>.
                        </li>
                        <li><a title="Click for more info" data-toggle="modal" data-target="#harm-prevention-modal"
                            data-backdrop="true" data-keyboard="true">Don't harm me</a>.</li>
                        <li><a title="Click for more info" data-toggle="modal"  data-target="#sharing-research-modal"
                            data-backdrop="true" data-keyboard="true">Share your research</a> from my data with the
                            public under open access terms.</li>
                    </ul>
                    <p>However, there are limits to the power of Terms of Use, and there are no guarantees that
                        users will respect all of these conditions. Please make sure to watch the entire video in the
                        following stage of the consent process - it is vital that you understand these considerations if
                        you are going to provide informed consent and participate in public genomics research.</p>
                </div>
                <div class="actions">
                    <input type="submit" class="btn primary" value="I understand">
                </div>

            </div>

        </fieldset>
    </s:form>

    <div id="your-requests-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Your Requests</h3>
        </div>
        <div class="modal-body">
            <p>By signing up for the Portable Consent process, you're signing up for a system that is a 'commons' -
                meaning that while you've granted some rights in the previous phase of the consent process, you're going
                to reserve some rights as well. These are behaviors you can request, and they'll be implemented via
                'terms of use' - a contract that researchers will have to sign to gain access to the system. You can
                read more about the terms of use below.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="re-identification-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Re-identification</h3>
        </div>
        <div class="modal-body">
            <p>We are quickly learning that with powerful computers and good mathematicians, it is increasingly
                possible to uniquely identify people inside large data sets. This is done every day with social media
                and in the 'app' universe of phones, indeed, your personal data is the engine that powers the finances
                of the modern Web in many cases. Preventing re-identification is often one of the major goals of
                informed consent in its traditional context. Re-identification means that someone figures out that a
                given genotype or' health record in the CGR system is actually yours, and connects it to your name, your
                email, or another piece of data that directly identifies you. Researchers will sign a contract in which
                they agree that, even if they were able to identify you, they won't do it.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="harm-prevention-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Harm Prevention</h3>
        </div>
        <div class="modal-body">
            <p>Harm prevention is another major goal of informed consent systems. It's one of the primary reasons we
                have so much fragmented data. There are a lot of imagined - and real - harms for public or commonly
                available genomic data. Someone could use information from your health and genomic backgrounds to infer
                paternity or other features of your family history, make claims that could affect your employment,
                insurance, or other financial services, incriminate relatives, make and place synthetic DNA as evidence,
                or reveal propensity for a disease that has no treatment. Researchers will sign a contract in which they
                agree that, even if they were in position to harm you, such as working at an insurance company, they
                won't do it.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="sharing-research-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Sharing Research</h3>
        </div>
        <div class="modal-body">
            <p>Since most research is published in scholarly journals, and most journals aren't available to the
                broader public due to cost and copyright concerns, researchers will have to commit to making their
                published research available under the Reproducible Research Standard. This means that the article
                itself must be 'Open Access' - online, free of charge, and free of most copyright restrictions. It also
                means that any software and data that is needed to replicate or build on the research described in the
                article is also online, free of charge, and free of most copyright restrictions. This means you'll be
                able to read the article, and that others who might want to build on the research will be able to do so
                as well.</p>
        </div>
        <div class="modal-footer"></div>
    </div>

    <div id="terms-of-use-modal" class="modal hide fade">
        <div class="modal-header">
            <a href="#" class="close" data-dismiss="modal">×</a>
            <h3>Terms of Use</h3>
        </div>
        <div class="modal-body">
            <p>Terms of use are the rules of the CGR system. They are embedded into a contract that researchers have
                to sign to access your data. These rules are legally binding on the researcher who signs them. If you've
                used sites like eBay or PayPal, you've used a website that uses terms of use to create community trust.
                If a researcher violates the terms of use, we will not only terminate their account, we'll name them
                publicly and make sure they don't come back.</p>
        </div>
        <div class="modal-footer"></div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#termsOfUseStep');
    });
</script>