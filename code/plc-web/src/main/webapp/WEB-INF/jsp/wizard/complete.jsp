<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="register_form">
    <h3>Your Profile Has Been Created</h3>
    <p>The last step is to log in to your account and upload your data. Use the user ID and password you entered on the previous screen.</p>
    <s:form namespace="/www/protected" action="uploadData/view">
        <div class="actions">
            <input type="submit" class="btn primary" value="Log in and Upload Data Now">
        </div>
    </s:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#signInStep');
    });
</script>