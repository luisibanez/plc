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
package com.fiveamsolutions.plc.web.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.transfer.Patient;
import com.fiveamsolutions.plc.service.PatientInformationService;
import com.google.inject.Inject;

/**
 * Defines REST operations for patient data.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Path("/patient")
@RequestScoped
public class PatientResource {
    private final PatientInformationService patientInformationService;

    /**
     * Class constructor.
     *
     * @param service the patient information service
     */
    @Inject
    public PatientResource(PatientInformationService service) {
        this.patientInformationService = service;
    }

    /**
     * Submits a patient's information, creating an account for them and returning their generated GUID.
     * @param patient the patient to create
     * @return the patient's GUID
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String registerPatient(Patient patient) {
        PatientAccount patientAccount = new PatientAccount(patient);
        return patientInformationService.registerPatient(patientAccount);
    }

    /**
     * Submits a patients data, uploading it to the system.
     * @param guid the guid of the patient to upload the information to
     * @param patientData the data to upload
     */
    @Path("{guid}")
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void uploadPatientData(@PathParam("guid") String guid, PatientData patientData) {
        patientInformationService.addPatientData(guid, patientData);
    }
}