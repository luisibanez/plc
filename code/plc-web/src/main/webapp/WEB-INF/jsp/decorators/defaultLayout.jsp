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
            <div id="logout_link">
                <c:if test="${sessionScope.loggedIn}">
                    <s:url value="/www/login/logout.action" var="logoutUrl"/>
                    <a href="${logoutUrl}"><span class="label">logout</span></a>
                </c:if>
            </div>
            <div id="consent_header" class="page-header">
                <h1>
                    Consent to Research <small>Upload Data</small>
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
            </div>
        </div>
    </div>
</body>
</html>