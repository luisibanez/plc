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
package com.fiveamsolutions.plc.web.struts2.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PLCUser;
import com.fiveamsolutions.plc.jaas.UserPrincipal;
import com.fiveamsolutions.plc.service.PatientInformationService;
import com.opensymphony.xwork2.Action;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class UploadDataActionTest {
    private UploadDataAction action;
    private PLCUser user;

    /**
     * Prepares test data.
     * @throws Exception on error
     */
    @Before
    public void prepareTestData() {
        user = TestPLCEntityFactory.createPLCUser();
        user.setId(1L);
        PatientAccountDao accountDao = mock(PatientAccountDao.class);
        when(accountDao.getByUsername(anyString())).thenReturn(TestPLCEntityFactory.createPatientAccount());
        action = new UploadDataAction(mock(PatientInformationService.class), accountDao);
        setPrincipalProxy(action, user);
    }

    /**
     * Tests execute.
     */
    @Test
    public void execute() {
        assertEquals(Action.SUCCESS, action.execute());
    }

    /**
     * Tests data upload.
     */
    @Test
    public void upload() {
        URL uploadUrl = UploadDataAction.class.getClassLoader().getResource("upload-data.txt");
        action.setDataFile(FileUtils.toFile(uploadUrl));
        action.setDataFileFileName("upload-data.txt");
        action.setTags("ACTION,TAGS");
        assertEquals(Action.SUCCESS, action.upload());
    }


    /**
     * Sets the principal proxy for the given action. The principal will be set to the currentTestUser.
     *
     * @param testAction for which to set the principal proxy.
     */
    private void setPrincipalProxy(UploadDataAction testAction, PLCUser user) {
        PrincipalProxy testPrincipalProxy = mock(PrincipalProxy.class);
        when(testPrincipalProxy.getUserPrincipal()).thenReturn(new UserPrincipal(user));
        testAction.setPrincipalProxy(testPrincipalProxy);
    }
}
