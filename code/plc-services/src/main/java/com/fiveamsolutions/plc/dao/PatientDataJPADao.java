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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.enums.FileSizeUnit;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;


/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientDataJPADao extends AbstractPLCEntityDao<PatientData> implements PatientDataDao {

    /**
     * Class constructor.
     * @param em the entity manager
     */
    @Inject
    PatientDataJPADao(EntityManager em) {
        super(em);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public List<PatientData> getByAccountId(Long id) {
        StringBuilder builder = new StringBuilder();
        builder.append("select pd from ").append(getEntityType().getName())
        .append(" pd where pd.patientAccount.id = :id");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("id", id);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Summary getPatientDataSummary(Filter filter) {
        Summary summary = new Summary();
        //First, grab un-filtered stats.
        StringBuilder builder = new StringBuilder();
        builder.append("select count(pd.id) as file_count, count(distinct pa.guid) as guid_count,")
            .append(" sum(pd.file_data_size) as data_sum from  patient_data as pd inner join patient_account as pa on")
            .append(" pd.patient_account_id = pa.id where 1 = 1");
        Query query = getEntityManager().createNativeQuery(builder.toString());
        Object[] results = (Object[]) query.getSingleResult();
        summary.setTotalFileCount(((BigInteger) results[0]).intValue());
        summary.setTotalPGUIDCount(((BigInteger) results[1]).intValue());

        long fileSize = (BigDecimal) results[2] == null ? 0L : ((BigDecimal) results[2]).longValue();
        summary.setTotalFileSize(normalizeFileSize(fileSize));
        summary.setTotalFileSizeUnit(FileSizeUnit.getUnit(fileSize));

        //If filtered values have been provided, gather those statistics, otherwise we're done.
        if (filter.valuesProvided()) {
            appendFilterQuery(filter, builder);

            query = getEntityManager().createNativeQuery(builder.toString());
            populateQueryParams(filter, query);

            results = (Object[]) query.getSingleResult();
            summary.setFilteredFileCount(((BigInteger) results[0]).intValue());
            summary.setFilteredPGUIDCount(((BigInteger) results[1]).intValue());

            long filteredFileSize = (BigDecimal) results[2] == null ? 0L : ((BigDecimal) results[2]).longValue();
            summary.setFilteredFileSize(normalizeFileSize(filteredFileSize));
            summary.setFilteredFileSizeUnit(FileSizeUnit.getUnit(filteredFileSize));
        }
        return summary;
    }

    private void appendFilterQuery(Filter filter, StringBuilder builder) {
        if (CollectionUtils.isNotEmpty(filter.getPguids())) {
            builder.append(" and pa.guid in (:pguids) ");
        }
        if (CollectionUtils.isNotEmpty(filter.getTags())) {
            builder.append(" and (select count(pt.tag) from patient_data_tags as pt where pt.tag in (:tags) and "
                    + " pd.id = pt.patient_data_id) > 0");
        }
        if (filter.getLastChangeDate() != null) {
            builder.append(" and pd.uploaded_date >= :changeDate");
        }
    }

    private void populateQueryParams(Filter filter, Query query) {
        if (CollectionUtils.isNotEmpty(filter.getPguids())) {
            query.setParameter("pguids", filter.getPguids());
        }
        if (CollectionUtils.isNotEmpty(filter.getTags())) {
            query.setParameter("tags", filter.getTags());
        }
        if (filter.getLastChangeDate() != null) {
            query.setParameter("changeDate", filter.getLastChangeDate());
        }
    }

    /**
     * Normalizes the file sie to match the file size unit.
     * @param size the size
     * @return the normalize size
     */
    private long normalizeFileSize(long size) {
        long normalizedSize;
        if (size / FileUtils.ONE_TB > 0) {
            normalizedSize = size / FileUtils.ONE_TB;
        } else if (size / FileUtils.ONE_GB > 0) {
            normalizedSize = size / FileUtils.ONE_GB;
        } else if (size / FileUtils.ONE_MB > 0) {
            normalizedSize = size / FileUtils.ONE_MB;
        } else if (size / FileUtils.ONE_KB > 0) {
            normalizedSize = size / FileUtils.ONE_KB;
        } else {
            normalizedSize = size;
        }
        return normalizedSize;
    }
}
