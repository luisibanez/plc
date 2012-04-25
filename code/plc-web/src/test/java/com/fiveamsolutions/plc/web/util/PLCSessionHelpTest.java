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
package com.fiveamsolutions.plc.web.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.web.struts2.util.PLCSessionHelper;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PLCSessionHelpTest {

    /**
     * Test session storage and retrieval of the patient demographics.
     */
    @Test
    public void patientDemographics() {
        Map<String, Object> session = new HashMap<String, Object>();
        PatientDemographics pd = new PatientDemographics();
        PLCSessionHelper.setPatientDemographics(pd, session);
        PatientDemographics retrieved = PLCSessionHelper.getPatientDemographics(session);
        assertSame(pd, retrieved);
    }

    /**
     * Test session storage and retrieval of the patient guid.
     */
    @Test
    public void patientGuid() {
        Map<String, Object> session = new HashMap<String, Object>();
        String guid = "guid";
        PLCSessionHelper.setPatientGUID(guid, session);
        String retrieved = PLCSessionHelper.getPatientGUID(session);
        assertEquals(guid, retrieved);
    }


    /**
     * Test session storage and retrieval of the current user.
     */
    @Test
    public void currentUser() {
        final Map<String, Object> sessionMap = new HashMap<String, Object>();
        HttpSession session = mock(HttpSession.class);
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                sessionMap.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), anyObject());
        when(session.getAttribute(anyString())).thenAnswer(new Answer<Object>() {
           public Object answer(InvocationOnMock invocation) {
               String key = (String) invocation.getArguments()[0];
               return sessionMap.get(key);
           }
        });
        PLCSessionHelper.setLoggedIn(Boolean.TRUE, session);
        Boolean retrieved = PLCSessionHelper.getLoggedIn(session);
        assertTrue(retrieved);
    }
}
