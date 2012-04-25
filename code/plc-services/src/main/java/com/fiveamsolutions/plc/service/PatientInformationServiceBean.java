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
package com.fiveamsolutions.plc.service;

import java.security.NoSuchAlgorithmException;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.google.inject.Inject;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientInformationServiceBean implements PatientInformationService {
    private final PatientAccountDao patientAccountDao;
    private final EncodingUtils encodingUtils;

    /**
     * Class constructor.
     * @param appResources the application resources
     * @param patientAccountDao the patient account dao
     * @throws NoSuchAlgorithmException on error
     */
    @Inject
    public PatientInformationServiceBean(PLCApplicationResources appResources, PatientAccountDao patientAccountDao)
            throws NoSuchAlgorithmException {
        this.patientAccountDao = patientAccountDao;
        this.encodingUtils = new EncodingUtils(appResources);
    }

    /**
     * {@inheritDoc}
     */
    public String registerPatient(PatientAccount patient) {
        String guid = encodingUtils.generatePatientGUID(patient.getPatientDemographics());
        StringBuilder builder = new StringBuilder().append(patient.getPlcUser().getSalt())
                .append(patient.getPlcUser().getPassword());
        patient.getPlcUser().setPassword(encodingUtils.hashString(builder.toString()));
        patient.setGuid(guid);
        patientAccountDao.save(patient);
        return guid;
    }
}
