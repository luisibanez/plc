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

import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.PatientDemographics;

/**
 * Interface for interacting with patient information.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public interface PatientInformationService {

    /**
     * Registers a patient in the system, returning their GUID.
     * @param patient the patient to register
     * @return the patient's GUID
     */
    String registerPatient(PatientAccount patient);

    /**
     * Generates the patient's GUID from the given patient data.
     * @param patientDemographics the patient demographics to generate the GUID from
     * @return the generated GUID
     */
    String generatePatientGUID(PatientDemographics patientDemographics);

    /**
     * Adds patient data to the patient account the given guid.
     * @param guid the guid of the account to add the data to
     * @param patientData the patient data to add
     */
    void addPatientData(String guid, PatientData patientData);
}
