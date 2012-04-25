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

import java.util.List;

import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.transfer.DownloadDetails;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;

/**
 *
 * Interface for interacting with patient data.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public interface PatientDataService {

    /**
     * Adds patient data to the patient account the given guid.
     * @param guid the guid of the account to add the data to
     * @param patientData the patient data to add
     */
    void addPatientData(String guid, PatientData patientData);

    /**
     * The patient data associated with the given guid.
     * @param guid the guid of the patient to retrieve patient data for
     * @return the patient data
     */
    List<PatientData> getPatientData(String guid);

    /**
     * Returns the summary statistics for the given filter.
     * @param filter the filter
     * @return the summary
     */
    Summary getSummary(Filter filter);

    /**
     * Retrieves and constructs the file to be downloaded by the researcher, returning vital information about the
     * download.
     * @param filter the filter to restrict the data by
     * @return the download details, if no filter is provided, download details will be empty.
     */
    DownloadDetails getDownloadDetails(Filter filter);

    /**
     * Removes all files from a predetermined location on the file system that are older than a specific age.
     */
    void cleanupExpiredDownloads();
}
