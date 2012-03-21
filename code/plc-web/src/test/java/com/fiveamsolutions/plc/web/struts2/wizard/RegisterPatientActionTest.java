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
package com.fiveamsolutions.plc.web.struts2.wizard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PLCUser;
import com.fiveamsolutions.plc.service.PatientInformationService;
import com.fiveamsolutions.plc.service.PatientInformationServiceBean;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;
import com.fiveamsolutions.plc.web.struts2.util.PLCSessionHelper;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class RegisterPatientActionTest extends AbstractConsentWizardTest<RegisterPatientAction> {

    private RegisterPatientAction testAction;
    private PatientInformationService patientInfoService;

    /**
     * Set up test data.
     * @throws Exception on error
     */
    @Override
    @Before
    public void prepareTestData() throws Exception {
        PatientAccountDao paDao = mock(PatientAccountDao.class);
        patientInfoService = new PatientInformationServiceBean(TestApplicationResourcesFactory.getApplicationResources(), paDao);
        testAction = new RegisterPatientAction(patientInfoService) {
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
    public RegisterPatientAction getTestAction() {
        return testAction;
    }

    /**
     * Tests patient registration.
     */
    @Test
    public void register() {
        RegisterPatientAction action = getTestAction();
        PLCSessionHelper.setPatientDemographics(TestPLCEntityFactory.createPatientDemographics(), action.getSession());
        PLCUser user = TestPLCEntityFactory.createPLCUser();
        action.setUser(user);
        assertEquals(ActionSupport.SUCCESS, action.register());
    }

    /**
     * Tests validation.
     */
    @Test
    public void validate() {
        RegisterPatientAction action = getTestAction();
        PLCUser user = TestPLCEntityFactory.createPLCUser();
        action.setUser(user);

        assertNull(action.getRepeatPassword());
        assertNull(action.getRepeatEmail());
        assertNull(action.getChallengeQuestion());
        assertNull(action.getChallengeAnswer());
        action.validate();

        assertTrue(action.hasFieldErrors());
        assertEquals(6, action.getFieldErrors().size());
        assertNotNull(action.getFieldErrors().get("repeatPassword"));
        assertNotNull(action.getFieldErrors().get("repeatEmail"));
        assertNotNull(action.getFieldErrors().get("user.password"));
        assertNotNull(action.getFieldErrors().get("challengeQuestion"));
        assertNotNull(action.getFieldErrors().get("challengeAnswer"));

        action.clearErrorsAndMessages();
        action.setChallengeQuestion("Question");
        action.setChallengeAnswer("Answer");
        action.setRepeatPassword("notTheSame");
        action.setRepeatEmail("notTheSame");
        action.validate();

        assertTrue(action.hasFieldErrors());
        assertEquals(4, action.getFieldErrors().size());
        assertNotNull(action.getFieldErrors().get("repeatPassword"));
        assertNotNull(action.getFieldErrors().get("user.password"));
        assertNotNull(action.getFieldErrors().get("repeatEmail"));
        assertNotNull(action.getFieldErrors().get("user.email"));

        action.clearErrorsAndMessages();
        action.setRepeatEmail(action.getUser().getEmail());
        action.setRepeatPassword(action.getUser().getPassword());
        action.validate();
        assertFalse(action.hasFieldErrors());
    }

}
