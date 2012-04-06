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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.PatientDataDao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.transfer.Patient;
import com.fiveamsolutions.plc.service.PatientDataService;
import com.fiveamsolutions.plc.service.PatientDataServiceBean;
import com.fiveamsolutions.plc.service.PatientInformationService;
import com.fiveamsolutions.plc.service.PatientInformationServiceBean;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientResourceTest {
    private static final int EXPECTED_GUID_LENGTH = 64;
    private PatientResource patientResource;
    private PatientDataService patientDataService;
    private PatientInformationService patientInformationService;
    private PatientAccountDao patientAccountDao;
    private PatientDataDao patientDataDao;

    /**
     * Sets up the test.
     * @throws Exception on error
     */
    @Before
    public void setUp() throws Exception {
        patientAccountDao = mock(PatientAccountDao.class);
        patientDataDao  = mock(PatientDataDao.class);
        patientInformationService =
                new PatientInformationServiceBean(TestApplicationResourcesFactory.getApplicationResources(),
                        patientAccountDao);
        patientDataService = new PatientDataServiceBean(patientAccountDao, patientDataDao,
                TestApplicationResourcesFactory.getApplicationResources());
        patientResource = new PatientResource(patientInformationService, patientDataService);
    }

    /**
     * Tests registering a patient via the REST-ful interface.
     * @throws Exception on error
     */
    @Test
    public void registerPatient() throws Exception {
        Patient patient = TestPLCEntityFactory.createPatient();
        String guid = patientResource.registerPatient(patient);
        assertNotNull(guid);
        assertEquals(EXPECTED_GUID_LENGTH, guid.length());
    }

    /**
     * Tests uploading patient data via the REST-ful interface
     * @throws Exception on error
     */
    @Test
    public void uploadPatientData() throws Exception {
        Patient patient = TestPLCEntityFactory.createPatient();
        String guid = patientResource.registerPatient(patient);
        assertNotNull(guid);

        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        patientAccount.setGuid(guid);
        when(patientAccountDao.getByGuid(anyString())).thenReturn(patientAccount);

        PatientData pd = TestPLCEntityFactory.createPatientData();
        patientResource.uploadPatientData(guid, pd);
    }
}
