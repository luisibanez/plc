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
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.fiveamsolutions.plc.web.struts2.util.AbstractTestHttpServletRequest;
import com.opensymphony.xwork2.Action;


/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class LogoutActionTest {

    /**
     * Test successful logout.
     */
    @Test
    public void testSuccessfulLogout() {
        LogoutAction testAction = new LogoutAction();
        HttpSession testSession = mock(HttpSession.class);
        HttpServletRequest testRequest = getTestServletRequest(testSession);
        verifyLogout(testAction, testRequest);
    }

    private HttpServletRequest getTestServletRequest(final HttpSession testSession) {
        return new AbstractTestHttpServletRequest() {
            /**
             * {@inheritDoc}
             */
            @Override
            public HttpSession getSession() {
                return testSession;
            }

        };
    }

    private void verifyLogout(LogoutAction testAction, HttpServletRequest testRequest) {
        try {
            testAction.setServletRequest(testRequest);
            assertEquals(Action.SUCCESS, testAction.logout());
            verify(testRequest.getSession()).invalidate();
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
