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
package com.fiveamsolutions.plc.data.validator;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fiveamsolutions.plc.data.PLCUser;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, PLCUser>, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private static Provider<EntityManager> entityManagerProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(UniqueUsername parameters) {
        //Do nothing.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(PLCUser value, ConstraintValidatorContext context) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(e) from ").append(value.getClass().getName())
            .append(" e where e.username = :username");
        if (value.getId() != null) {
            builder.append(" and e.id != :id");
        }
        Query query = entityManagerProvider.get().createQuery(builder.toString());
        query.setParameter("username", value.getUsername());
        if (value.getId() != null) {
            query.setParameter("id", value.getId());
        }
        query.setFlushMode(FlushModeType.COMMIT);
        long result = (Long) query.getSingleResult();
        return result == 0;
    }
}
