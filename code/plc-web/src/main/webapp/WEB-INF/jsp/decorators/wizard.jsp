<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<fmt:message var="pageTitle" key="pageTitle" />
<title><decorator:title default="${pageTitle}" /></title>
<link type="image/x-icon" rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" />
<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/styles/plc.css'/>" />
<link type="text/css" rel="stylesheet" media="all"
    href="<c:url value='/scripts/jquery/css/smoothness/jquery-ui-1.8.18.custom.css'/>" />
<%@ include file="/WEB-INF/jsp/common/scripts.jsp"%>
<decorator:head />
</head>
<body>
    <div id="bootstrap" />
    <div id="container" class="container">
        <div id="content" class="wrapper">
            <div id="logout_link"></div>
            <div id="consent_header" class="page-header">
                <h1>
                    Consent to Research <small>Getting Consent</small>
                </h1>
            </div>
            <div id="row" class="row">
                <div id="span10" class="span10">
                    <decorator:body />
                </div>
                <div id="span4" class="span4">
                    <div id="sidebar">
                        <h6>Get Informed</h6>
                        <ul>
                            <li id="welcomeStep">Welcome</li>
                            <li id="willingnessStep">Willingness to Participate</li>
                            <li id="termsOfUseStep">Researcher Terms of Use</li>
                            <li id="grantRightsStep">Grant Rights</li>
                            <li id="watchVideoStep">Watch Video</li>
                            <li id="checkpointStep">Checkpoint</li>
                            <li id="acknowledgeUnderstandingStep">Acknowledge Understanding</li>
                        </ul>
                        <h6>Consent to Research</h6>
                        <ul>
                            <li id="consentFormStep">Consent Form</li>
                            <li id="generateIdStep">Generate ID</li>
                        </ul>
                        <h6>Upload Your Data</h6>
                        <ul>
                            <li id="userProfileStep">User Profile</li>
                            <li id="signInStep">Sign In</li>
                            <li id="uploadDataStep">Upload Data</li>
                        </ul>
                    </div>
                </div>
                <div id="agreement_help" style="display: none;">
                    <h6>Table of Contents</h6>
                    <p>
                        <a href="#description_of_volunteer_population">Description of Volunteer Population</a>
                    </p>
                    <p>
                        <a href="#what_is_informed_consent">What is Informed Consent</a>
                    </p>
                    <p>
                        <a href="#who_is_eligible_to_participate_in_this_study">Who is Eligible to Participate in this Study</a>
                    </p>
                    <p>
                        <a href="#short_description_of_the_cgr_framework">Short Description of the CGR Framework</a>
                    </p>
                    <p>
                        <a href="#long_description_of_the_cgr_framework">Long Description of the CGR Framework</a>
                    </p>
                    <p>
                        <a href="#creating_the_cgr_framework">Creating the CGR Framework</a>
                    </p>
                    <p>
                        <a href="#about_portable_legal_consent">About Portable Legal Consent</a>
                    </p>
                    <p>
                        <a href="#about_this_informed_consent_document">About this Informed Consent Document</a>
                    </p>

                    <p>
                        Article I. <a href="#purpose">PURPOSE</a>
                    </p>
                    <p>
                        Article II. <a href="#overview">OVERVIEW</a>
                    </p>
                    <p>
                        Article III. <a href="#duration_and_participation">DURATION and PARTICIPATION</a>
                    </p>
                    <p>
                        Article IV. <a href="#additional_study_procedures">Additional Study PROCEDURES</a>
                    </p>
                    <p>
                        Article V. <a href="#collection,_publication_and_return_of_data">COLLECTION, PUBLICATION and
                            RETURN of DATA</a>
                    </p>
                    <p>
                        Article VI. <a href="#risks_and_discomforts">RISKS AND DISCOMFORTS</a>
                    </p>
                    <p>
                        Article VII. <a href="#benefits">BENEFITS</a>
                    </p>
                    <p>
                        Article VIII. <a href="#intellectual_property">INTELLECTUAL PROPERTY</a>
                    </p>
                    <p>
                        Article IX. <a href="#confidentiality">CONFIDENTIALITY</a>
                    </p>
                    <p>
                        Article X. <a href="#refusal_or_withdrawal_of_participation">REFUSAL OR WITHDRAWAL OF
                            PARTICIPATION</a>
                    </p>
                    <p>
                        Article XI. <a href="#alternatives">ALTERNATIVES</a>
                    </p>
                    <p>
                        Article XII.<a href="#research-related_contact_information"> RESEARCH-RELATED CONTACT
                            INFORMATION</a>
                    </p>
                    <p>
                        <a href="#signature">SIGNATURE</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>