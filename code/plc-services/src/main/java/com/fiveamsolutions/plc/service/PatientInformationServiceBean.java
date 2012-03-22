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
package com.fiveamsolutions.plc.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.PatientDataDao;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientInformationServiceBean implements PatientInformationService {
    private final PatientAccountDao patientAccountDao;
    private final PatientDataDao patientDataDao;
    private final EncodingUtils encodingUtils;

    /**
     * Class constructor.
     * @param appResources the application resources
     * @param patientAccountDao the patient account dao
     * @param patientDataDao the patient data dao
     * @throws NoSuchAlgorithmException if hashing algorithm isn't found
     */
    @Inject
    public PatientInformationServiceBean(PLCApplicationResources appResources, PatientAccountDao patientAccountDao,
            PatientDataDao patientDataDao)
            throws NoSuchAlgorithmException {
        this.patientAccountDao = patientAccountDao;
        this.patientDataDao = patientDataDao;
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addPatientData(String guid, PatientData patientData) {
        PatientAccount account = patientAccountDao.getByGuid(guid);
        if (account == null) {
            return;
        }
        patientData.setPatientAccount(account);
        account.getPatientData().add(patientData);
        patientDataDao.save(patientData);
    }

    /**
     * The patient data associated with the given guid.
     * @param guid the guid of the patient to retrieve patient data for
     * @return the patient data
     */
    @Override
    @Transactional
    public List<PatientData> getPatientData(String guid) {
        List<PatientData> results = new ArrayList<PatientData>();
        PatientAccount account = patientAccountDao.getByGuid(guid);
        if (account == null) {
            return results;
        }
        return patientDataDao.getByAccountId(account.getId());
    }

}
