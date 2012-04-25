/*******************************************************************************
 * Copyright (c) 2012, 5AM Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the 
 * following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following 
 * disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following 
 * disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package com.fiveamsolutions.plc.web.struts2.wizard;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.service.EncodingUtils;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.fiveamsolutions.plc.web.struts2.util.PLCSessionHelper;
import com.google.inject.Inject;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class GenerateGuidAction extends ConsentWizardAction {

    private static final long serialVersionUID = 1L;
    private final EncodingUtils encodingUtils;
    @Valid
    private PatientDemographics patientDemographics = new PatientDemographics();


    /**
     * Class constructor.
     *
     * @param appResources the application resources
     * @throws NoSuchAlgorithmException on error
     */
    @Inject
    public GenerateGuidAction(PLCApplicationResources appResources) throws NoSuchAlgorithmException {
        this.encodingUtils = new EncodingUtils(appResources);
    }

    /**
     * Generates a patient's GUID.
     * @return the struts forwarding result
     */
    public String generateId() {
        String guid = encodingUtils.generatePatientGUID(patientDemographics);
        PLCSessionHelper.setPatientDemographics(patientDemographics, getSession());
        PLCSessionHelper.setPatientGUID(guid, getSession());
        return SUCCESS;
    }

    /**
     * @return the patientDemographics
     */
    public PatientDemographics getPatientDemographics() {
        return patientDemographics;
    }

    /**
     * @param patientDemo the patientDemographics to set
     */
    public void setPatientData(PatientDemographics patientDemo) {
        this.patientDemographics = patientDemo;
    }
}
