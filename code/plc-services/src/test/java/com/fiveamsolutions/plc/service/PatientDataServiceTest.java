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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.PatientDataDao;
import com.fiveamsolutions.plc.dao.PatientDataJPADao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.enums.FileSizeUnit;
import com.fiveamsolutions.plc.data.transfer.DownloadDetails;
import com.fiveamsolutions.plc.data.transfer.FileInfo;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientDataServiceTest {
    private PatientDataServiceBean patientDataServiceBean;
    private PatientAccountDao patientAccountDao;
    private PatientDataDao patientDataDao;

    /**
     * Setup test.
     * @throws Exception on error
     */
    @Before
    public void prepareTestData() throws Exception {
        patientAccountDao = mock(PatientAccountDao.class);
        patientDataDao = mock(PatientDataJPADao.class);
        when(patientDataDao.normalizeFileSize(anyLong())).thenCallRealMethod();
        patientDataServiceBean = new PatientDataServiceBean(patientAccountDao, patientDataDao,
                TestApplicationResourcesFactory.getApplicationResources());
    }

    /**
     * Tests adding patient data to an account.
     */
    @Test
    public void addPatientData() {
        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        when(patientAccountDao.getByGuid(anyString())).thenReturn(patientAccount);

        String guid = patientAccount.getGuid();

        patientDataServiceBean.addPatientData(guid, TestPLCEntityFactory.createPatientData());
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

        String guid = patientAccount.getGuid();
        patientDataServiceBean.addPatientData(guid, TestPLCEntityFactory.createPatientData());
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

        String guid = patientAccount.getGuid();

        patientDataServiceBean.addPatientData(guid, TestPLCEntityFactory.createPatientData());
        assertFalse(patientAccount.getPatientData().isEmpty());
        assertEquals(1, patientAccount.getPatientData().size());

        List<PatientData> results = patientDataServiceBean.getPatientData(guid);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    /**
     * Tests patient data retrieval for non existent guid.
     */
    @Test
    public void getPatientDataDoesntExist() {
        List<PatientData> results = patientDataServiceBean.getPatientData("wrong");
        assertTrue(results.isEmpty());
    }

    /**
     * Tests retrieval of patient data when no filter parameters are set.
     */
    @Test
    public void getDownloadDetailsNoFilter() {
        DownloadDetails details = patientDataServiceBean.getDownloadDetails(new Filter());
        assertEquals(0, details.getSize());
        assertEquals(FileSizeUnit.B, details.getUnit());
        assertTrue(StringUtils.isEmpty(details.getUrl()));
        assertNull(details.getExpirationDate());
    }

    /**
     * Tests retrieval of patient data when nothing matches the given filter.
     */
    @Test
    public void getDownloadDetailsNoResults() {
        Filter filter = new Filter();
        filter.getTags().add("UNKNOWN");
        DownloadDetails details = patientDataServiceBean.getDownloadDetails(filter);
        assertEquals(0, details.getSize());
        assertEquals(FileSizeUnit.B, details.getUnit());
        assertTrue(StringUtils.isEmpty(details.getUrl()));
        assertNull(details.getExpirationDate());
    }


    /**
     * Tests retrieval of patient data and writing of it to zip file.
     */
    @Test
    public void getDownloadDetails() {
        List<FileInfo> files = new ArrayList<FileInfo>();
        for (int i = 0 ; i < 100; i++) {
            PatientData data = TestPLCEntityFactory.createPatientData();
            files.add(new FileInfo(data.getFileName(), data.getFileData()));
        }

        when(patientDataDao.getPatientData(any(Filter.class))).thenReturn(files);
        Filter filter = new Filter();
        filter.getTags().add("KNOWN");
        File file = null;
        try {
            DownloadDetails details = patientDataServiceBean.getDownloadDetails(filter);
            assertTrue(StringUtils.isNotEmpty(details.getUrl()));
            assertNotNull(details.getExpirationDate());
            assertEquals(FileSizeUnit.KB, details.getUnit());
            assertEquals(19, details.getSize());
            String downloadPath =
                    TestApplicationResourcesFactory.getApplicationResources().getStringResource("file.storage.location");
            file = new File(downloadPath + File.separator + details.getUrl() + ".zip");
            assertTrue(file.exists());
        } finally {
            FileUtils.deleteQuietly(file);
        }
    }

    /**
     * Tests removal of old download files from the file system.
     */
    @Test
    public void fileCleanup() throws IOException {
        String downloadPath =
                TestApplicationResourcesFactory.getApplicationResources().getStringResource("file.storage.location");
        File newFile = new File(downloadPath + File.separator + "testFile.zip");
        FileUtils.touch(newFile);
        assertTrue(newFile.exists());

        patientDataServiceBean.cleanupExpiredDownloads();
        assertFalse(newFile.exists());
    }
}
