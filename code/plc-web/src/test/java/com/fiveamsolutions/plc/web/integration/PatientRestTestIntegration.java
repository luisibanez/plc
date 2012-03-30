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
package com.fiveamsolutions.plc.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.enums.Country;
import com.fiveamsolutions.plc.data.enums.PatientDataSource;
import com.fiveamsolutions.plc.data.enums.PatientDataType;
import com.fiveamsolutions.plc.data.transfer.Patient;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Integration test for the patient REST inteface.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PatientRestTestIntegration extends AbstractRestTestIntegration {
    private static final int RESULT_LENGTH = 64;

    /**
     * Tests registration of a patient via REST.
     */
    @Test
    public void registerPatient() {
        WebResource resource = getWebResource("patient");
        Patient p = generatePatient("testuser");
        String result =
                resource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON_TYPE).post(String.class, p);
        assertNotNull(result);
        assertEquals(RESULT_LENGTH, result.length());
    }

    /**
     * Tests uploading of a patient's data via REST.
     */
    @Test
    public void uploadPatientData() {
        WebResource resource = getWebResource("patient");
        Patient p = generatePatient("patientDataUser");
        String guid =
                resource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON_TYPE).post(String.class, p);
        assertNotNull(guid);

        PatientData pd = generatePatientData();
        resource = getWebResource("patient");
        resource.addFilter(new HTTPBasicAuthFilter(p.getUsername(), p.getPassword()));
        resource.path(guid).type(MediaType.APPLICATION_JSON_TYPE).entity(pd).post();
    }

    /**
     * Generates a patient object with the given username.
     * @param username the username to use
     * @return a newly constructed patient
     */
    private Patient generatePatient(String username) {
        Patient p = new Patient();
        p.setUsername(username);
        p.setEmail(username + "@example.com");
        p.setPassword("password");
        p.setBirthDate(DateUtils.addYears(new Date(), -RandomUtils.nextInt(90)));
        p.setFirstName(username);
        p.setBirthName("User");
        p.setBirthCountry(Country.US);
        p.setBirthPlace("Somewhere");
        p.setFullName("Full Name");
        return p;
    }

    /**
     * Generates patient data for testing.
     * @return the patient data to upload
     */
    private PatientData generatePatientData() {
        PatientData patientData = new PatientData();
        patientData.setDataType(PatientDataType.SNP_GENOTYPE);
        patientData.setDataSource(PatientDataSource.TWENTY_THREE_AND_ME);
        patientData.setVersion("v1");
        patientData.setNotes("This is a test file.");
        patientData.setFileName("patient_data.txt");

        try {
            InputStream inputData = TestPLCEntityFactory.class.getClassLoader().getResourceAsStream("patient_data.txt");
            patientData.setFileData(IOUtils.toByteArray(inputData));
        } catch (IOException e) {
            fail("Error loading test data.");
        }
        List<String> tags = new ArrayList<String>();
        tags.add("REST");
        tags.add("Integration Testing");
        patientData.setTags(tags);
        return patientData;
    }
}
