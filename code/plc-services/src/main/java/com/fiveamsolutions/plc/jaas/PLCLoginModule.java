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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.plc.dao.PLCUserDao;
import com.fiveamsolutions.plc.data.PLCUser;
import com.fiveamsolutions.plc.service.EncodingUtils;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Login module for PLC.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PLCLoginModule implements LoginModule {
    private static final Logger LOG = Logger.getLogger(PLCLoginModule.class);
    private static final String DEFAULT_ROLE = "plcuser";
    @Inject
    private static Provider<PLCUserDao> userDaoProvider;
    @Inject
    private static Provider<PLCApplicationResources> appResourcesProvider;

    private CallbackHandler handler;
    private Subject subject;
    private EncodingUtils encodingUtils;
    private PLCUser user;


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean abort() throws LoginException {
        LOG.debug("abort");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean commit() throws LoginException {
        LOG.debug("commit");
        subject.getPrincipals().add(new UserPrincipal(user));
        subject.getPrincipals().add(new UserRole(DEFAULT_ROLE));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Subject aSubject, CallbackHandler cbHandler, Map<String, ?> sharedState,
            Map<String, ?> opts) {
        LOG.debug("initialize");
        this.subject = aSubject;
        this.handler = cbHandler;
        try {
            this.encodingUtils = new EncodingUtils(getAppResources());
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Algorithm not found.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.PreserveStackTrace")
    @Override
    public boolean login() throws LoginException {
        NameCallback nameCallback = new NameCallback("Username: ");
        PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);
        Callback[] callBacks = new Callback[] {
                nameCallback,
                passwordCallback
        };
        try {
            handler.handle(callBacks);
        } catch (IOException e) {
            LOG.error("Callback error", e);
            throw new LoginException("Unable to handle callbacks: " + e.getMessage());
        } catch (UnsupportedCallbackException e) {
            LOG.error("Callback error", e);
            throw new LoginException("Unable to handle callbacks: " + e.getMessage());
        }
        String username = nameCallback.getName();
        user = userDaoProvider.get().getByUsername(username);
        if (user == null) {
            LOG.warn("Invalid username/password: " + username);
            throw new FailedLoginException("Invalid username/password");
        }

        char[] password = passwordCallback.getPassword();
        StringBuilder builder = new StringBuilder().append(user.getSalt()).append(password);
        String hashedPassword = encodingUtils.hashString(builder.toString());
        if (!StringUtils.equals(user.getPassword(), hashedPassword)) {
            LOG.warn("Invalid username/password: " + username);
            throw new FailedLoginException("Invalid username/password");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean logout() throws LoginException {
        LOG.debug("logout");
        subject.getPrincipals().clear();
        return true;
    }

    private PLCApplicationResources getAppResources() {
        return appResourcesProvider.get();
    }
}
