<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="understand_form">
    <div class="alert-message warning">If you don't feel like you know enough about public genetic research to
        move forward, or are uncomfortable now that you know, this is a good time to stop.</div>

    <ul>
        <li><a href="http://sagebase.org/" target="_blank">Please take me back to the Sage Bionetworks home
                page</a>.</li>
        <li><a href="http://www.pgpstudy.org/" target="_blank">Please take me to resources on public genetics</a>.</li>
        <li><a href="http://trentcenter.duke.edu/research/bioethics" target="_blank">Please take me to
                resources on bioethics and informed consent</a>.</li>
    </ul>
    <s:form cssClass="form-stacked" name="understand" namespace="/www/wizard" action="step7" method="post">
        <fieldset>
            <div class="actions">
                <input type="submit" class="btn primary" value="I understand, and want to proceed">
            </div>
        </fieldset>
    </s:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        markProgress('#checkpointStep');
    });
</script>