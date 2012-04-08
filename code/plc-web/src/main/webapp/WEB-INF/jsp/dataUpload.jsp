<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="uploadData">
    <div id="take_survey_task">
        <h3>
            1. Take Our Survey
            <c:choose>
                <c:when test="${surveyTaken}">
                    <span class="label success">Done</span>
                </c:when>
                <c:otherwise>
                    <span class="label warning">To Do</span>
                </c:otherwise>
            </c:choose>
        </h3>
        <p>Thanks for helping us make our informed consent process better by alpha testing. But you're not off the hook yet. 
        Please take the survey below - it'll only take a few minutes - so that we can learn where we need to improve. 
        This data will form part of our submission to the required privacy and ethics authorities, so it's vital that you fill it out.</p>
        
        <div class="actions">
            <s:form id="surveyForm" namespace="/www/protected" action="uploadData/takeSurvey" method="post">
                <a id="surveyLink" href="https://www.surveymonkey.com/s/DJZ3WSH" target="_blank" class="btn">Take the survey</a>
            </s:form>
        </div>
    </div>

    <h3>
        2. Upload Your Data
        <c:choose>
            <c:when test="${not empty retrievedPatientData}">
                <span class="label success">Done</span>
            </c:when>
            <c:otherwise>
                <span class="label warning">To Do</span>
            </c:otherwise>
        </c:choose>
    </h3>
    <p>Select a file from your computer to upload. You may upload genotype data from personal genomics providers
        (e.g. 23AndME, Navigenics, deCODEme, etc.) or medical data, such as Blue Button, My Family Health Portrait or
        other electronic health records.</p>
    
    <c:if test="${not empty retrievedPatientData}">
        <table>
            <thead>
                <tr>
                    <th>File</th>
                    <th>Source</th>
                    <th>Version</th>
                    <th>Uploaded Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${retrievedPatientData}" var="data">
                    <tr>
                        <td>${data.fileName}</td>
                        <td>${data.dataSource.code}</td>
                        <td>${data.version}</td>
                        <td><fmt:formatDate type="both" value="${data.uploadedDate}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <s:form cssClass="form-stacked" id="uploadDataForm" namespace="/www/protected" action="uploadData/upload" method="post"
        enctype="multipart/form-data">
        <fieldset>
            <div class="clearfix">
                <label for="dataFile">Select the file to upload:&nbsp;&nbsp;</label>
                <div class="input">
                    <s:file id="dataFile" name="dataFile" />
                    <s:fielderror fieldName="dataFile" cssClass="error-message" />
                </div>
            </div>
            <div class="clearfix">
                <label for="">Data contained in file</label>
                <div class="input">
                    <s:select id="dataType" cssClass="xlarge required" name="patientData.dataType"
                        list="%{@com.fiveamsolutions.plc.data.enums.PatientDataType@values()}" listValue="code"
                        cssErrorClass="error-field" />
                    <s:fielderror fieldName="patientData.dataType" cssClass="error-message" />
                </div>
            </div>
            <div class="clearfix">
                <label for="">Source</label>
                <div class="input">
                    <s:select id="dataSource" cssClass="xlarge required" name="patientData.dataSource"
                        list="%{@com.fiveamsolutions.plc.data.enums.PatientDataSource@values()}" listValue="code"
                        cssErrorClass="error-field" />
                    <s:fielderror fieldName="patientData.dataSource" cssClass="error-message" />
                </div>
            </div>
            <div class="clearfix">
                <label for="">Version</label>
                <div class="input">
                    <s:textfield id="version" name="patientData.version" />
                    <s:fielderror fieldName="patientData.version" cssClass="error-message" />
                </div>
            </div>
            <div class="clearfix">
                <label for="">Tags (optional)</label>
                <div class="input">
                    <s:textfield id="tags" name="tags" />
                    <p>Enter any keywords you can think of, separated by commas.</p>
                    <s:fielderror fieldName="tags" cssClass="error-message" />
                </div>

            </div>
            <div class="clearfix">
                <label for="">Write anything else you'd like to say about this file</label>
                <div class="input">
                    <s:textarea id="notes" name="patientData.notes" />
                    <s:fielderror fieldName="patientData.notes" cssClass="error-message" />
                </div>
            </div>
            <div class="actions">
                <s:submit label="Upload" id="uploadButton" cssClass="btn primary" />
            </div>
        </fieldset>
    </s:form>
</div>
<div id="confirmationMessage" style="display: none">
    <p>You are uploading your personal medical or genomic data. Once you click confirm, it will be uploaded to a database
    where researchers can use it to build models of disease and drug response.</p>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        surveyLinkHandler();
        markProgress('#uploadDataStep');
    	var confirmDialog = 
        $('#confirmationMessage').dialog({
        	autoOpen: false,
            modal: true,
            buttons: {
                "Confirm": function() {
                    $(this).dialog('close');
                    $('#uploadDataForm').submit();
                 },
                 Cancel: function() {
                	$(this).dialog('close');
                }
            }
        });
    	$("#uploadButton").click(function(e) {
    		$(confirmDialog).dialog('open');
    		return false;
    	});
    });
</script>