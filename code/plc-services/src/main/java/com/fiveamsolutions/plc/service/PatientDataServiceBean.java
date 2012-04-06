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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.PatientDataDao;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.enums.FileSizeUnit;
import com.fiveamsolutions.plc.data.transfer.DownloadDetails;
import com.fiveamsolutions.plc.data.transfer.FileInfo;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientDataServiceBean implements PatientDataService {
    private static final Logger LOG = Logger.getLogger(PatientDataServiceBean.class);
    private static final String FILE_LOCATION_KEY = "file.storage.location";
    private static final String FILE_LIFETIME_KEY = "file.storage.duration";
    private static final String FILE_DOWNLOAD_URL_KEY = "file.download.url";

    private final PatientDataDao patientDataDao;
    private final PatientAccountDao patientAccountDao;
    private final PLCApplicationResources appResources;

    /**
     * Class constructor.
     * @param patientAccountDao the patient account dao
     * @param patientDataDao the patient data dao
     * @param appResources the application resources
     */
    @Inject
    public PatientDataServiceBean(PatientAccountDao patientAccountDao, PatientDataDao patientDataDao,
            PLCApplicationResources appResources) {
        this.patientDataDao = patientDataDao;
        this.patientAccountDao = patientAccountDao;
        this.appResources = appResources;
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

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Summary getSummary(Filter filter) {
        return patientDataDao.getPatientDataSummary(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DownloadDetails getDownloadDetails(Filter filter) {
        DownloadDetails details = new DownloadDetails();
        if (!filter.valuesProvided()) {
            return details;
        }
        List<FileInfo> info = patientDataDao.getPatientData(filter);
        if (CollectionUtils.isNotEmpty(info)) {
            try {
                String location = appResources.getStringResource(FILE_LOCATION_KEY);
                String url = appResources.getStringResource(FILE_DOWNLOAD_URL_KEY);
                String fileName = UUID.randomUUID().toString();
                File zipFile = new File(location + File.separator + fileName + ".zip");
                ZipOutputStream zipFileOutput = new ZipOutputStream(new FileOutputStream(zipFile));
                for (FileInfo fileInfo : info) {
                    zipFileOutput.putNextEntry(new ZipEntry(fileInfo.getFileName()));
                    IOUtils.write(fileInfo.getFileData(), zipFileOutput);
                    zipFileOutput.closeEntry();
                }
                IOUtils.closeQuietly(zipFileOutput);
                long fileLength = zipFile.length();
                details.setUrl(url + "/" + fileName);
                details.setSize(patientDataDao.normalizeFileSize(fileLength));
                details.setUnit(FileSizeUnit.getUnit(fileLength));
                int minutesToLive = appResources.getIntResource(FILE_LIFETIME_KEY);
                details.setExpirationDate(DateUtils.addMinutes(new Date(), minutesToLive));
            } catch (IOException e) {
                LOG.error("Error constructing file to download.", e);
            }
        }
        return details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cleanupExpiredDownloads() {
        int ageInMinutes = appResources.getIntResource(FILE_LIFETIME_KEY);
        String location = appResources.getStringResource(FILE_LOCATION_KEY);

        Date cutOffDate = DateUtils.addMinutes(new Date(), -ageInMinutes);
        Collection<File> files = FileUtils.listFiles(new File(location), FileFilterUtils.and(
                FileFilterUtils.suffixFileFilter(".zip"), FileFilterUtils.ageFileFilter(cutOffDate, true)), null);
        for (File file : files) {
            FileUtils.deleteQuietly(file);
        }
    }
}
