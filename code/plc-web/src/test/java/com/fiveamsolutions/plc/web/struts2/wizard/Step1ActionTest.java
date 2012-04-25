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

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class Step1ActionTest extends AbstractConsentWizardTest<Step1Action> {
    private Step1Action action;

    /**
     * Prepares test data.
     */
    @Override
    @Before
    public void prepareTestData() throws Exception {
        action = new Step1Action();
        super.prepareTestData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Step1Action getTestAction() {
        return action;
    }

    /**
     * Tests the start action.
     */
    @Test
    public void start() {
        assertEquals(ActionSupport.SUCCESS, action.start());
    }
}
