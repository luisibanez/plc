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
package com.fiveamsolutions.plc.jaas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.plc.dao.PLCUserDao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PLCUser;
import com.fiveamsolutions.plc.service.EncodingUtils;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provider;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PLCLoginModuleTest {
    private PLCUserDao userDao;
    private PLCApplicationResources appResouces;
    private EncodingUtils encodingUtils;
    private Subject subject;
    private PLCUser testUser;
    private static final String TEST_USERNAME = "testusername";
    private static final String TEST_PASSWORD = "testpassword";

    /**
     * Prepare test data.
     * @throws Exception on error
     */
    @Before
    public void prepareTestData() throws Exception {
        subject = new Subject();
        userDao = mock(PLCUserDao.class);
        appResouces = TestApplicationResourcesFactory.getApplicationResources();
        encodingUtils = new EncodingUtils(appResouces);
        testUser = TestPLCEntityFactory.createPLCUser();
        testUser.setId(1L);
        testUser.setUsername(TEST_USERNAME);
        testUser.setPassword(encodingUtils.hashString(testUser.getSalt() + TEST_PASSWORD));
    }

    /**
     * Tests abort.
     *
     * @throws LoginException if login module throws an exception
     */
    @Test
    public void abort() throws LoginException {
        PLCLoginModule loginModule = getTestLoginModule();
        loginModule.initialize(null, null, null, null);
        assertFalse(loginModule.abort());

    }

    /**
     * Test login IOException raised by the callback handler.
     *
     * @throws UnsupportedCallbackException if the callback handler doesn't support the exception
     * @throws IOException if the callback handler throws such an exception
     */
    @Test
    public void callbackHandlerIOException() throws IOException, UnsupportedCallbackException {
        PLCLoginModule loginModule = getTestLoginModule();
        CallbackHandler callbackHandler = mock(CallbackHandler.class);
        doThrow(new IOException("Test IOException")).when(callbackHandler).handle(any(Callback[].class));
        loginModule.initialize(null, callbackHandler, null, null);
        try {
            loginModule.login();
            fail("LoginException expected");
        } catch (LoginException e) {
            assertEquals("Unable to handle callbacks: Test IOException", e.getMessage());
        }
    }

    /**
     * Test login exception raised by the callback handler.
     *
     * @throws UnsupportedCallbackException if the callback handler doesn't support the exception
     * @throws IOException if the callback handler throws such an exception
     */
    @Test
    public void callbackHandlerUnsupportedCallbackException() throws IOException, UnsupportedCallbackException {
        PLCLoginModule loginModule = getTestLoginModule();
        CallbackHandler callbackHandler = mock(CallbackHandler.class);
        doThrow(new UnsupportedCallbackException(null, "Test Unsupported Callback")).when(callbackHandler).handle(
                any(Callback[].class));
        loginModule.initialize(null, callbackHandler, null, null);
        try {
            loginModule.login();
            fail("LoginException expected");
        } catch (LoginException e) {
            assertEquals("Unable to handle callbacks: Test Unsupported Callback", e.getMessage());
        }
    }

    /**
     * Test unsuccessful login with username that doesn't exist.
     *
     * @throws LoginException if the login is not successful.
     */
    @Test(expected = LoginException.class)
    public void wrongUsernameLogin() throws LoginException {
        when(userDao.getByUsername(any(String.class))).thenReturn(null);
        invokeLogin(TEST_USERNAME, TEST_PASSWORD);
    }

    /**
     * Test unsuccessful login with incorrect password.
     *
     * @throws LoginException if the login is not successful.
     */
    @Test(expected = LoginException.class)
    public void wrongPasswordLogin() throws LoginException {
        when(userDao.getByUsername(eq(TEST_USERNAME))).thenReturn(testUser);
        invokeLogin(TEST_USERNAME, "Wrong");
    }

    /**
     * Test successful login.
     *
     * @throws LoginException if the login is not successful.
     */
    @Test
    public void successfulLogin() throws LoginException {
        when(userDao.getByUsername(eq(TEST_USERNAME))).thenReturn(testUser);
        invokeLogin(TEST_USERNAME, TEST_PASSWORD);
    }

    /**
     * Tests logout.
     *
     * @throws LoginException on error
     */
    @Test
    public void logout() throws LoginException {
        when(userDao.getByUsername(eq(TEST_USERNAME))).thenReturn(testUser);
        PLCLoginModule testLoginModule = invokeLogin(TEST_USERNAME, TEST_PASSWORD);
        assertTrue(subject.getPrincipals().contains(new UserRole("plcuser")));
        assertTrue(subject.getPrincipals().contains(new UserPrincipal(testUser)));
        testLoginModule.logout();
        assertFalse(subject.getPrincipals().contains(new UserRole("plcuser")));
        assertFalse(subject.getPrincipals().contains(new UserPrincipal(testUser)));
    }

    private PLCLoginModule invokeLogin(String userName, String password) throws LoginException {
        PLCLoginModule testLoginModule = getTestLoginModule();
        try {
            CallbackHandler cbHandler = createNameCallbackHandler(userName, password.toCharArray());
            testLoginModule.initialize(subject, cbHandler, null, null);
            testLoginModule.login();
            testLoginModule.commit();
        } catch (IOException e) {
            fail("Unexpected IOException " + e.getMessage());
        } catch (UnsupportedCallbackException e) {
            fail("Unexpected UnsupportedCallbackException " + e.getMessage());
        }
        return testLoginModule;
    }

    /**
     * Create a callback handler that can handle name and password callbacks.
     *
     * @param userName to set in the appropriate callback
     * @param password to set in the appropriate callback
     * @return a callback handler
     * @throws UnsupportedCallbackException if the callback handler doesn't support the exception
     * @throws IOException if the callback handler throws such an exception
     */
    private CallbackHandler createNameCallbackHandler(final String userName, final char[] password) throws IOException,
            UnsupportedCallbackException {
        CallbackHandler mockCallbackHandler = mock(CallbackHandler.class);
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Callback[] callbacks = (Callback[]) invocation.getArguments()[0];
                for (Callback cb : callbacks) {
                    if (cb instanceof NameCallback) {
                        NameCallback ncb = (NameCallback) cb;
                        ncb.setName(userName);
                    } else if (cb instanceof PasswordCallback) {
                        PasswordCallback pcb = (PasswordCallback) cb;
                        pcb.setPassword(password);
                    } else {
                        throw new UnsupportedCallbackException(callbacks[0]);
                    }
                }
                return null;
            }
        }).when(mockCallbackHandler).handle(any(Callback[].class));
        return mockCallbackHandler;
    }

    private PLCLoginModule getTestLoginModule() {
        PLCLoginModule loginModule = new PLCLoginModule();
        final Provider<PLCUserDao> userDaoProvider = createServiceProvider(userDao);
        final Provider<PLCApplicationResources> appResourceProvider = createServiceProvider(appResouces);
        Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(PLCUserDao.class).toProvider(userDaoProvider);
                bind(PLCApplicationResources.class).toProvider(appResourceProvider);
                requestStaticInjection(PLCLoginModule.class);
            }
        });
        return loginModule;
    }

    private <T> Provider<T> createServiceProvider(final T service) {
        return new Provider<T>() {
            @Override
            public T get() {
                return service;
            }
        };
    }
}
