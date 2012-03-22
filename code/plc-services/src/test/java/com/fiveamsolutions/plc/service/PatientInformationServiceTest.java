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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.PatientDataDao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientInformationServiceTest {
    private static final int EXPECTED_GUID_LENGTH = 64;
    private PatientInformationServiceBean patientInformationServiceBean;
    private PatientAccountDao patientAccountDao;
    private PatientDataDao patientDataDao;

    /**
     * Setup test.
     * @throws Exception on error
     */
    @Before
    public void prepareTestData() throws Exception {
        patientAccountDao = mock(PatientAccountDao.class);
        patientDataDao = mock(PatientDataDao.class);
        patientInformationServiceBean =
                new PatientInformationServiceBean(TestApplicationResourcesFactory.getApplicationResources(),
                        patientAccountDao, patientDataDao);
    }

    /**
     * Tests patient GUID generation.
     */
    @Test
    public void registerPatient() {
        PatientAccount patientAcount = TestPLCEntityFactory.createPatientAccount();
        String guid = patientInformationServiceBean.registerPatient(patientAcount);

        assertTrue(StringUtils.isNotEmpty(guid));
        assertEquals(EXPECTED_GUID_LENGTH, guid.length());
        assertEquals(guid, patientInformationServiceBean.registerPatient(patientAcount));

        PatientAccount diffPatientAccount = TestPLCEntityFactory.createPatientAccount();
        diffPatientAccount.getPatientDemographics().setBirthDate(DateUtils.addDays(new Date(), 1));
        String differentGuid = patientInformationServiceBean.registerPatient(diffPatientAccount);
        assertTrue(StringUtils.isNotEmpty(differentGuid));
        assertEquals(EXPECTED_GUID_LENGTH, differentGuid.length());
        assertFalse(StringUtils.equals(guid, differentGuid));
    }

    /**
     * Tests adding patient data to an account.
     */
    @Test
    public void addPatientData() {
        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        when(patientAccountDao.getByGuid(anyString())).thenReturn(patientAccount);

        String guid = patientInformationServiceBean.registerPatient(patientAccount);
        assertTrue(StringUtils.isNotEmpty(guid));
        assertEquals(EXPECTED_GUID_LENGTH, guid.length());
        assertTrue(patientAccount.getPatientData().isEmpty());

        patientInformationServiceBean.addPatientData(guid, TestPLCEntityFactory.createPatientData());
        assertFalse(patientAccount.getPatientData().isEmpty());
        assertEquals(1, patientAccount.getPatientData().size());
    }

    /**
     * Tests adding patient data to an account that doesn't exist.
     */
    @Test
    public void addPatientDataToInvalidAccount() {
        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        when(patientAccountDao.getByGuid(anyString())).thenReturn(null);

        String guid = patientInformationServiceBean.registerPatient(patientAccount);
        assertTrue(StringUtils.isNotEmpty(guid));
        assertEquals(EXPECTED_GUID_LENGTH, guid.length());
        assertTrue(patientAccount.getPatientData().isEmpty());

        patientInformationServiceBean.addPatientData(guid, TestPLCEntityFactory.createPatientData());
        assertTrue(patientAccount.getPatientData().isEmpty());
    }

    /**
     * Tests patient data retrieval.
     */
    @Test
    public void getPatientData() {
        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        when(patientAccountDao.getByGuid(anyString())).thenReturn(patientAccount);
        when(patientDataDao.getByAccountId(anyLong())).thenReturn(patientAccount.getPatientData());

        String guid = patientInformationServiceBean.registerPatient(patientAccount);
        assertTrue(StringUtils.isNotEmpty(guid));
        assertEquals(EXPECTED_GUID_LENGTH, guid.length());
        assertTrue(patientAccount.getPatientData().isEmpty());

        patientInformationServiceBean.addPatientData(guid, TestPLCEntityFactory.createPatientData());
        assertFalse(patientAccount.getPatientData().isEmpty());
        assertEquals(1, patientAccount.getPatientData().size());

        List<PatientData> results = patientInformationServiceBean.getPatientData(guid);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    /**
     * Tests patient data retrieval for non existent guid.
     */
    @Test
    public void getPatientDataDoesntExist() {
        List<PatientData> results = patientInformationServiceBean.getPatientData("wrong");
        assertTrue(results.isEmpty());
    }
}
