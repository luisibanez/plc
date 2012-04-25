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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.fiveamsolutions.plc.data.PatientAccount;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientAccountJPADao extends AbstractPLCEntityDao<PatientAccount> implements PatientAccountDao {

    /**
     * Class constructor.
     * @param em the entity manager
     */
    @Inject
    PatientAccountJPADao(EntityManager em) {
        super(em);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public PatientAccount getByGuid(String guid) {
        StringBuilder builder = new StringBuilder();
        builder.append("select pa from ").append(getEntityType().getName()).append(" pa where pa.guid = :guid");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("guid", guid);
        PatientAccount pa = null;
        List<PatientAccount> results = query.getResultList();
        if (CollectionUtils.isNotEmpty(results)) {
            pa = results.get(0);
        }
        return pa;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public PatientAccount getByUsername(String username) {
        StringBuilder builder = new StringBuilder();
        builder.append("select pa from ").append(getEntityType().getName())
            .append(" pa where pa.plcUser.username = :username");

        Query query = getEntityManager().createQuery(builder.toString());
        query.setParameter("username", username);
        PatientAccount pa = null;
        List<PatientAccount> results = query.getResultList();
        if (CollectionUtils.isNotEmpty(results)) {
            pa = results.get(0);
        }
        return pa;
    }


}
