/**
 * Copyright (c) 2012, 5AM Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - Neither the name of the author nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.fiveamsolutions.plc.web.struts2.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.jaas.UserPrincipal;
import com.fiveamsolutions.plc.service.PatientDataService;
import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class UploadDataAction extends ActionSupport implements PrincipalAware {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(UploadDataAction.class);

    private List<PatientData> retrievedPatientData;

    private PrincipalProxy principalProxy;
    private final PatientDataService patientDataService;
    private final PatientAccountDao patientDao;
    @NotNull
    private File dataFile;
    private String dataFileFileName;
    private PatientData patientData = new PatientData();
    private String tags;
    private boolean surveyTaken = false;

    /**
     * Class constructor.
     *
     * @param patientDataService the patient data service
     * @param accountDao the patient account dao
     */
    @Inject
    public UploadDataAction(PatientDataService patientDataService, PatientAccountDao accountDao) {
        this.patientDataService = patientDataService;
        this.patientDao = accountDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SkipValidation
    public String execute() {
        PatientAccount account = patientDao.getByUsername(getUserPrincipal().getName());
        List<PatientData> data = patientDataService.getPatientData(account.getGuid());
        setRetrievedPatientData(data);
        surveyTaken = account.isSurveyTaken();
        return SUCCESS;
    }

    /**
     * Upload patient data.
     *
     * @return struts2 forwarding result
     */
    public String upload() {
        PatientAccount account = patientDao.getByUsername(getUserPrincipal().getName());
        PatientData pa = new PatientData();
        pa.setDataSource(patientData.getDataSource());
        pa.setDataType(patientData.getDataType());
        pa.setVersion(patientData.getVersion());
        pa.setNotes(patientData.getNotes());
        pa.setTags(Arrays.asList(StringUtils.split(getTags(), ",")));
        pa.setFileName(getDataFileFileName());
        try {
            pa.setFileData(FileUtils.readFileToByteArray(getDataFile()));
        } catch (IOException e) {
            LOG.error("Error reading file data.", e);
        }
        patientDataService.addPatientData(account.getGuid(), pa);
        return SUCCESS;
    }

    /**
     * Marks the survey as taken.
     *
     * @return struts2 forwarding result
     */
    @SkipValidation
    public String markSurveyTaken() {
        PatientAccount account = patientDao.getByUsername(getUserPrincipal().getName());
        account.setSurveyTaken(true);
        patientDao.save(account);
        setSurveyTaken(true);
        return SUCCESS;
    }

    /**
     * @return the retrievedPatientData
     */
    public List<PatientData> getRetrievedPatientData() {
        return retrievedPatientData;
    }

    /**
     * @param retrievedPatientData the retrievedPatientData to set
     */
    public void setRetrievedPatientData(List<PatientData> retrievedPatientData) {
        this.retrievedPatientData = retrievedPatientData;
    }

    /**
     * @return the dataFile
     */
    public File getDataFile() {
        return dataFile;
    }

    /**
     * @param dataFile the dataFile to set
     */
    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * @return the dataFileFileName
     */
    public String getDataFileFileName() {
        return dataFileFileName;
    }

    /**
     * @param dataFileFileName the dataFileFileName to set
     */
    public void setDataFileFileName(String dataFileFileName) {
        this.dataFileFileName = dataFileFileName;
    }

    /**
     * @return the patientData
     */
    public PatientData getPatientData() {
        return patientData;
    }

    /**
     * @param patientData the patientData to set
     */
    public void setPatientData(PatientData patientData) {
        this.patientData = patientData;
    }

    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return the principalProxy
     */
    public PrincipalProxy getPrincipalProxy() {
        return principalProxy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrincipalProxy(PrincipalProxy principalProxy) {
        this.principalProxy = principalProxy;
    }

    /**
     * @return the corresponding plc user principal.
     */
    protected UserPrincipal getUserPrincipal() {
        return (UserPrincipal) getPrincipalProxy().getUserPrincipal();
    }

    /**
     * @return the surveyTaken
     */
    public boolean isSurveyTaken() {
        return surveyTaken;
    }

    /**
     * @param surveyTaken the surveyTaken to set
     */
    public void setSurveyTaken(boolean surveyTaken) {
        this.surveyTaken = surveyTaken;
    }
}
