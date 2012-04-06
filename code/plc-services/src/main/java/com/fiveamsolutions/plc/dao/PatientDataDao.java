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
package com.fiveamsolutions.plc.dao;

import java.util.List;

import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.transfer.FileInfo;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public interface PatientDataDao extends Dao<PatientData> {

    /**
     * Retrieves all patient data associated with the given account id.
     * @param id the account id
     * @return the patient data associated with the account
     */
    List<PatientData> getByAccountId(Long id);

    /**
     * Retrieves the patient data summary, filtering by the given filters.
     * @param filter the values to filter the filtered results by
     * @return the summary
     */
    Summary getPatientDataSummary(Filter filter);

    /**
     * Retrieves all of the patient file data that matches the given filter.
     * If no filter settings are provided, an empty list it returned.
     * @param filter the filter values
     * @return the matching file data
     */
    List<FileInfo> getPatientData(Filter filter);

    /**
     * Normalizes the file sie to match the file size unit.
     * @param size the size
     * @return the normalize size
     */
    long normalizeFileSize(long size);
}
