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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;
import com.opensymphony.xwork2.Action;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class GenerateGuidActionTest extends AbstractConsentWizardTest<GenerateGuidAction> {
    private GenerateGuidAction testAction;

    @Override
    public void prepareTestData() throws Exception {
        testAction = new GenerateGuidAction(TestApplicationResourcesFactory.getApplicationResources()) {
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
    protected GenerateGuidAction getTestAction() {
        return testAction;
    }

    /**
     * Tests guid generation.
     */
    @Test
    public void generateGuid() {
        GenerateGuidAction action = getTestAction();
        PatientDemographics patientDemographics = TestPLCEntityFactory.createPatientDemographics();
        action.setPatientData(patientDemographics);
        assertEquals(Action.SUCCESS, action.generateId());
        assertNotNull(action.getSession().get("guid"));
        assertNotNull(action.getSession().get("patientDemographics"));
    }

}
