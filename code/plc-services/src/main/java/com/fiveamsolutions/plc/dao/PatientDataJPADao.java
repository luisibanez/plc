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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.enums.FileSizeUnit;
import com.fiveamsolutions.plc.data.transfer.FileInfo;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.PatientDataStats;
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
        StringBuilder builder = generateQuery(filter, true, false);
        Query query = getEntityManager().createQuery(builder.toString());
        PatientDataStats stats = (PatientDataStats) query.getSingleResult();
        summary.setTotalFileCount(stats.getFileCount());
        summary.setTotalPGUIDCount(stats.getGuidCount());
        summary.setTotalFileSize(normalizeFileSize(stats.getFileDataSize()));
        summary.setTotalFileSizeUnit(FileSizeUnit.getUnit(stats.getFileDataSize()));

        //If filtered values have been provided, gather those statistics, otherwise we're done.
        if (filter.valuesProvided()) {
            builder = generateQuery(filter, true, true);

            query = getEntityManager().createQuery(builder.toString());
            populateQueryParams(filter, query);

            stats = (PatientDataStats) query.getSingleResult();
            summary.setFilteredFileCount(stats.getFileCount());
            summary.setFilteredPGUIDCount(stats.getGuidCount());
            summary.setFilteredFileSize(normalizeFileSize(stats.getFileDataSize()));
            summary.setFilteredFileSizeUnit(FileSizeUnit.getUnit(stats.getFileDataSize()));
        }
        return summary;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<FileInfo> getPatientData(Filter filter) {
        List<FileInfo> fileInfo = new ArrayList<FileInfo>();
        if (!filter.valuesProvided()) {
            return fileInfo;
        }
        StringBuilder builder = generateQuery(filter, false, true);
        Query query = getEntityManager().createQuery(builder.toString());
        populateQueryParams(filter, query);

        fileInfo.addAll(query.getResultList());
        return fileInfo;
    }

    /**
     * Generates the necessary sql.
     * @param params the filter
     * @param count whether or not the query should be for counts or actual objects
     * @param filter whether to filter the query or not
     * @return the sql query to run
     */
    private StringBuilder generateQuery(Filter params, boolean count, boolean filter) {
        StringBuilder builder = new StringBuilder();
        if (count) {
            builder.append("select new com.fiveamsolutions.plc.data.transfer.PatientDataStats(count(pd.id),")
                .append(" count(distinct pa.guid), sum(pd.fileDataSize)) from ").append(getEntityType().getName())
                .append(" as pd inner join pd.patientAccount as pa where 1 = 1");
        } else {
            builder.append("select new com.fiveamsolutions.plc.data.transfer.FileInfo(pd.fileName, pd.fileData) from  ")
                .append(getEntityType().getName()).append(" as pd inner join pd.patientAccount as pa where 1 = 1");
        }

        if (filter) {
            appendFilterQuery(params, builder);
        }
        return builder;
    }

    private void appendFilterQuery(Filter filter, StringBuilder builder) {
        if (CollectionUtils.isNotEmpty(filter.getPguids())) {
            builder.append(" and pa.guid in (:pguids) ");
        }
        if (CollectionUtils.isNotEmpty(filter.getTags())) {
            builder.append(" and (select count(tag) from pd.tags where tag in (:tags)) > 0 ");
        }
        if (filter.getLastChangeDate() != null) {
            builder.append(" and pd.uploadedDate >= :changeDate");
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
     * {@inheritDoc}
     */
    @Override
    public long normalizeFileSize(long size) {
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
