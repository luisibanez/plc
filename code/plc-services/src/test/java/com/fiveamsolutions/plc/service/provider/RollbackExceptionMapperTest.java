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
package com.fiveamsolutions.plc.service.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.fiveamsolutions.plc.data.transfer.StandardError;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class RollbackExceptionMapperTest {

    /**
     * Tests the rollback exception mapper.
     */
    @Test
    public void toResponseStandard() {
        RollbackExceptionMapper mapper = new RollbackExceptionMapper();
        RollbackException e = new RollbackException("Standard Rollback Exception");
        Response r = mapper.toResponse(e);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertNotNull(r.getEntity());
        StandardError error = (StandardError) r.getEntity();
        assertEquals("Standard Rollback Exception", error.getMessage());
    }

    /**
     * Tests the rollback exception mapper causes by validation errors.
     */
    @Test
    public void toResponseValidationErrors() {
        RollbackExceptionMapper mapper = new RollbackExceptionMapper();
        ConstraintViolation<?> cv = mock(ConstraintViolation.class);
        Path pp = mock(Path.class);
        when(cv.getPropertyPath()).thenReturn(pp);

        Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
        violations.add(cv);
        ConstraintViolationException cve = new ConstraintViolationException(violations);
        RollbackException e = new RollbackException(cve);
        Response r = mapper.toResponse(e);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus());
        assertNotNull(r.getEntity());
        List<StandardError> errors = (List<StandardError>) r.getEntity();
        assertEquals(1, errors.size());
    }

}
