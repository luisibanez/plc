<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="orientation_form">
    <h3>This is a complicated process. You will go through three sections.</h3>
    <p>
        <span class="label">First</span> You'll interact with the core ideas of informed consent for the Sage Commons.
    </p>
    <p>
        <span class="label">Second</span> You'll read the informed consent agreement, and digitally sign it.
    </p>
    <p>
        <span class="label">Third</span> You'll be able to start participating in public genomics research by uploading
        data.

    </p>
    <s:form namespace="/www/wizard" action="step2" method="post">
        <fieldset>
            <input type="submit" class="btn primary" value="I understand, and want to proceed">
        </fieldset>
    </s:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#welcomeStep');
    });
</script>