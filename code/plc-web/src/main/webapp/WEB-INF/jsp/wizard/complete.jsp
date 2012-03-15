<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="register_form">
    Registration successful.
    Your GUID is <s:property value="%{#session.guid}"/>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#signInStep');
    });
</script>