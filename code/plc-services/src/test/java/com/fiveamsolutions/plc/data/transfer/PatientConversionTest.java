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
package com.fiveamsolutions.plc.data.transfer;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONMarshaller;

/**
 * Tests conversion between Patient and PatientAccount.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PatientConversionTest {

    /**
     * Tests conversion between the Patient transfer object and the patient account.
     */
    @Test
    public void conversion() {
        Patient patient = TestPLCEntityFactory.createPatient();
        PatientAccount patientAccount = new PatientAccount(patient);

        assertEquals(patient.getUsername(), patientAccount.getPlcUser().getUsername());
        assertEquals(patient.getEmail(), patientAccount.getPlcUser().getEmail());
        assertEquals(patient.getPassword(), patientAccount.getPlcUser().getPassword());
        assertEquals(patient.getFirstName(), patientAccount.getPatientDemographics().getFirstName());
        assertEquals(patient.getBirthName(), patientAccount.getPatientDemographics().getBirthName());
        assertEquals(patient.getBirthDate(), patientAccount.getPatientDemographics().getBirthDate());
        assertEquals(patient.getBirthPlace(), patientAccount.getPatientDemographics().getBirthPlace());
        assertEquals(patient.getBirthCountry(), patientAccount.getPatientDemographics().getBirthCountry());
        assertEquals(patient.getChallengeQuestions().size(), patientAccount.getChallengeQuestions().size());
    }

    /**
     * Tests JSON marshalling.
     * @throws JAXBException on error
     */
    @Test
    public void jsonMarshalling() throws JAXBException {
        JAXBContext context = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(),
                Patient.class);
        JSONMarshaller marshaller = JSONJAXBContext.getJSONMarshaller(context.createMarshaller());

        StringWriter writer = new StringWriter();
        Patient patient = TestPLCEntityFactory.createPatient();
        marshaller.marshallToJSON(patient, writer);
        assertEquals(getPatientJson(patient),  writer.toString());
    }

    private String getPatientJson(Patient patient) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String dateString  = dateFormat.format(patient.getBirthDate());
        StringBuilder builder = new StringBuilder();
        builder.append("{").append("\n");
        builder.append("  \"email\" : \"").append(patient.getEmail()).append("\",\n");
        builder.append("  \"username\" : \"").append(patient.getUsername()).append("\",\n");
        builder.append("  \"password\" : \"").append(patient.getPassword()).append("\",\n");
        builder.append("  \"firstName\" : \"").append(patient.getFirstName()).append("\",\n");
        builder.append("  \"birthName\" : \"").append(patient.getBirthName()).append("\",\n");
        builder.append("  \"birthPlace\" : \"").append(patient.getBirthPlace()).append("\",\n");
        builder.append("  \"birthCountry\" : \"").append(patient.getBirthCountry()).append("\",\n");
        builder.append("  \"birthDate\" : \"").append(dateString).append("\",\n");
        builder.append("  \"recoveryQuestions\" : [ {\n");
        builder.append("    \"challengeQuestion\" : {\n");
        builder.append("      \"question\" : \"").append(patient.getChallengeQuestions().get(0).getQuestion()).append("\",\n");
        builder.append("      \"answer\" : \"").append(patient.getChallengeQuestions().get(0).getAnswer()).append("\"\n");
        builder.append("    }\n");
        builder.append("  } ]\n");
        builder.append("}");
        return builder.toString();
    }
}
