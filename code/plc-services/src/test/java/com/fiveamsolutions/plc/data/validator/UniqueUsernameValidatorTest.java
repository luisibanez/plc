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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.AbstractJPADaoTest;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientAccount;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class UniqueUsernameValidatorTest extends AbstractJPADaoTest {
    private Validator validator;
    private EntityManager em;

    /**
     * Prepares test data.
     */
    @Before
    public void prepareTestData() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        em = getEntityManager();
    }

    /**
     * Tests valid patient account.
     */
    @Test
    public void valid() {
        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        Set<ConstraintViolation<PatientAccount>> violations = validator.validate(patientAccount);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests invalid patient account.
     */
    @Test
    public void invalid() {
        PatientAccount patientAccount = TestPLCEntityFactory.createPatientAccount();
        PatientAccount duplicateAccount = TestPLCEntityFactory.createPatientAccount();
        duplicateAccount.setUsername(patientAccount.getUsername());
        try {
            em.getTransaction().begin();
            em.persist(patientAccount);
            em.getTransaction().commit();
            Set<ConstraintViolation<PatientAccount>> violations = validator.validate(patientAccount);
            assertTrue(violations.isEmpty());
            
            violations = validator.validate(duplicateAccount);
            assertFalse(violations.isEmpty());
            assertEquals(1, violations.size());
        } catch (Exception e) {
            em.getTransaction().rollback();
            fail(e.getMessage());
        }
    }
}
