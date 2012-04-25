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
package com.fiveamsolutions.plc.data.oauth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class OAuthTokenTest {
    private OAuthToken token;

    /**
     * Setup test data.
     */
    @Before
    public void prepareTestData() {
        token = TestPLCEntityFactory.createToken();
    }

    /**
     * Tests whether a user is a researcher and has been granted access.
     */
    @Test
    public void isInRoleResearcher() {
        token.setResearcher(true);
        assertFalse(token.isInRole(OAuthToken.RESEARCHER_ROLE));
        assertFalse(token.isInRole(OAuthToken.PLC_ROLE));

        token.setAuthorized(true);
        assertTrue(token.isInRole(OAuthToken.RESEARCHER_ROLE));
        assertTrue(token.isInRole(OAuthToken.PLC_ROLE));
    }

    /**
     * Tests whether a user is a regular user and has been granted access.
     */
    @Test
    public void isInRoleUser() {
        assertFalse(token.isInRole(OAuthToken.RESEARCHER_ROLE));
        assertFalse(token.isInRole(OAuthToken.PLC_ROLE));

        token.setAuthorized(true);
        assertFalse(token.isInRole(OAuthToken.RESEARCHER_ROLE));
        assertTrue(token.isInRole(OAuthToken.PLC_ROLE));
    }

}
