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
package com.fiveamsolutions.plc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.data.PLCEntity;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.enums.FileSizeUnit;
import com.fiveamsolutions.plc.data.transfer.FileInfo;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientDataJPADaoTest extends AbstractPLCJPADaoTest<PatientData> {
    private static final int PATIENT_DATA_COUNT = 100;
    private PatientDataJPADao testDao;

    /**
     * Prepares test data.
     */
    @Before
    public void prepareTestData() {
        testDao = new PatientDataJPADao(getEntityManager());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PatientDataJPADao getTestDao() {
        return testDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PatientData getTestEntity() {
        return TestPLCEntityFactory.createPatientData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void changeTestEntity(PLCEntity testEntity) {
        PatientData pd = (PatientData) testEntity;
        pd.setUploadedDate(DateUtils.addDays(pd.getUploadedDate(), 1));
    }

    /**
     * Tests retrieving patient data by account id.
     */
    @Test
    public void getByAccountId() {
        PatientData pd = persistTestEntity();
        PatientAccount pa = TestPLCEntityFactory.createPatientAccount();

        getTestDao().getEntityManager().getTransaction().begin();
        getTestDao().getEntityManager().persist(pa);
        pd.setPatientAccount(pa);
        getTestDao().save(pd);
        getTestDao().getEntityManager().getTransaction().commit();

        List<PatientData> results = getTestDao().getByAccountId(pa.getId());
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    /**
     * Tests retrieval of patient data summary information with no filter data set and no data in the db.
     */
    @Test
    public void patientDataSummaryNoFilterNoData() {
        Filter filter = new Filter();
        Summary summary = getTestDao().getPatientDataSummary(filter);
        assertEquals(0, summary.getTotalFileCount());
        assertEquals(0, summary.getTotalFileSize());
        assertEquals(FileSizeUnit.B, summary.getTotalFileSizeUnit());
        assertEquals(0, summary.getTotalPGUIDCount());
        assertEquals(0, summary.getFilteredFileCount());
        assertEquals(0, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.B, summary.getFilteredFileSizeUnit());
        assertEquals(0, summary.getFilteredPGUIDCount());
    }


    /**
     * Tests retrieval of patient data summary information with no filter data set.
     */
    @Test
    public void patientDataSummaryNoFilter() {
        persistMultipleTestEntities(PATIENT_DATA_COUNT);

        Filter filter = new Filter();
        Summary summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(0, summary.getFilteredFileCount());
        assertEquals(0, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.B, summary.getFilteredFileSizeUnit());
        assertEquals(0, summary.getFilteredPGUIDCount());
    }

    /**
     * Tests retrieval of patient data summary information with date filter set.
     */
    @Test
    public void patientDataSummaryDateFilter() {
        persistMultipleTestEntities(PATIENT_DATA_COUNT);

        Filter filter = new Filter();
        filter.setLastChangeDate(DateUtils.addDays(new Date(), 1));
        Summary summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(0, summary.getFilteredFileCount());
        assertEquals(0, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.B, summary.getFilteredFileSizeUnit());
        assertEquals(0, summary.getFilteredPGUIDCount());

        filter.setLastChangeDate(DateUtils.addDays(new Date(), -1));
        summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(PATIENT_DATA_COUNT, summary.getFilteredFileCount());
        assertEquals(2, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.KB, summary.getFilteredFileSizeUnit());
        assertEquals(PATIENT_DATA_COUNT, summary.getFilteredPGUIDCount());
    }

    /**
     * Tests retrieval of patient data summary information with tag filter set.
     */
    @Test
    public void patientDataSummaryTagFilter() {
        persistMultipleTestEntities(PATIENT_DATA_COUNT);

        Filter filter = new Filter();
        Set<String> tags = new HashSet<String>();
        tags.add("NO_SUCH");
        filter.setTags(tags);

        Summary summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(0, summary.getFilteredFileCount());
        assertEquals(0, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.B, summary.getFilteredFileSizeUnit());
        assertEquals(0, summary.getFilteredPGUIDCount());

        tags.add("Experimental");
        filter.setTags(tags);
        summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(PATIENT_DATA_COUNT, summary.getFilteredFileCount());
        assertEquals(2, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.KB, summary.getFilteredFileSizeUnit());
        assertEquals(PATIENT_DATA_COUNT, summary.getFilteredPGUIDCount());
    }


    /**
     * Tests retrieval of patient data summary information with guid filter set.
     */
    @Test
    public void patientDataSummaryGuidFilter() {
        persistMultipleTestEntities(PATIENT_DATA_COUNT);

        Filter filter = new Filter();
        Set<String> guids = new HashSet<String>();
        guids.add("NO_SUCH");
        filter.setPguids(guids);

        Summary summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(0, summary.getFilteredFileCount());
        assertEquals(0, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.B, summary.getFilteredFileSizeUnit());
        assertEquals(0, summary.getFilteredPGUIDCount());

        List<PatientData> data = getTestDao().getAll();
        guids.add(data.get(0).getPatientAccount().getGuid());
        guids.add(data.get(1).getPatientAccount().getGuid());

        filter.setPguids(guids);
        summary = getTestDao().getPatientDataSummary(filter);
        verifyTotalSummary(summary);
        assertEquals(2, summary.getFilteredFileCount());
        assertEquals(56, summary.getFilteredFileSize());
        assertEquals(FileSizeUnit.B, summary.getFilteredFileSizeUnit());
        assertEquals(2, summary.getFilteredPGUIDCount());
    }

    /**
     * Tests retrieval of file data by filter
     */
    @Test
    public void getPatientData() {
        persistMultipleTestEntities(PATIENT_DATA_COUNT);

        PatientData refData = TestPLCEntityFactory.createPatientData();

        Filter filter = new Filter();

        List<FileInfo> results = getTestDao().getPatientData(filter);
        assertTrue(results.isEmpty());

        Set<String> tags = new HashSet<String>();
        tags.add("Experimental");
        filter.setTags(tags);
        results = getTestDao().getPatientData(filter);
        assertEquals(PATIENT_DATA_COUNT, results.size());

        for (FileInfo info: results) {
            assertTrue(StringUtils.isNotEmpty(info.getFileName()));
            assertTrue(ArrayUtils.isNotEmpty(info.getFileData()));
            assertTrue(Arrays.equals(refData.getFileData(), info.getFileData()));
        }
    }

    private void verifyTotalSummary(Summary summary) {
        assertEquals(PATIENT_DATA_COUNT, summary.getTotalFileCount());
        assertEquals(2, summary.getTotalFileSize());
        assertEquals(FileSizeUnit.KB, summary.getTotalFileSizeUnit());
        assertEquals(PATIENT_DATA_COUNT, summary.getTotalPGUIDCount());
    }

    /**
     * Tests normalization of file size.
     */
    @Test
    public void normalizeFileSize() {
        assertEquals(1, getTestDao().normalizeFileSize(1L));
        assertEquals(1, getTestDao().normalizeFileSize(1025));
        assertEquals(1, getTestDao().normalizeFileSize(1025 * 1024));
        assertEquals(1, getTestDao().normalizeFileSize(1025 * 1024 * 1024));
        assertEquals(1, getTestDao().normalizeFileSize(1025 * 1024 * 1024 * 1024));
    }
}
