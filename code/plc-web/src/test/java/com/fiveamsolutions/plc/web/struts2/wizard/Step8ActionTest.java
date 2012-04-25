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
package com.fiveamsolutions.plc.web.struts2.wizard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class Step8ActionTest extends AbstractConsentWizardTest<Step8Action> {
    private Step8Action testAction;

    /**
     * Prepares test data.
     */
    @Override
    @Before
    public void prepareTestData() throws Exception {
        testAction = new Step8Action() {
            private static final long serialVersionUID = 1L;

            /**
             * {@inheritDoc}
             */
            @Override
            public String getText(String text) {
                return text;
            }
        };
        super.prepareTestData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Step8Action getTestAction() {
        return testAction;
    }

    /**
     * Tests validation.
     */
    @Test
    public void validate() {
        Step8Action action = getTestAction();
        assertFalse(action.isReadAndUnderstood());
        assertFalse(action.isAgreement());
        assertNull(action.getToday());
        assertTrue(StringUtils.isEmpty(action.getFullName()));

        action.validate();
        assertTrue(action.hasFieldErrors());
        assertEquals(4, action.getFieldErrors().size());
        assertNotNull(action.getFieldErrors().get("readAndUnderstood"));
        assertNotNull(action.getFieldErrors().get("agreement"));
        assertNotNull(action.getFieldErrors().get("fullName"));
        assertNotNull(action.getFieldErrors().get("today"));

        action.clearErrorsAndMessages();
        action.setReadAndUnderstood(true);
        action.setAgreement(true);
        action.setFullName("Test User");
        action.setToday(new Date());
        action.validate();
        assertFalse(action.hasFieldErrors());
    }
}
