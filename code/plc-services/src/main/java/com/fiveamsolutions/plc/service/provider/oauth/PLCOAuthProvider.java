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
package com.fiveamsolutions.plc.service.provider.oauth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.RandomStringUtils;

import com.fiveamsolutions.plc.dao.oauth.ConsumerDao;
import com.fiveamsolutions.plc.dao.oauth.TokenDao;
import com.fiveamsolutions.plc.data.oauth.OAuthToken;
import com.google.inject.Inject;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.server.spi.OAuthConsumer;
import com.sun.jersey.oauth.server.spi.OAuthProvider;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@Provider
public class PLCOAuthProvider implements OAuthProvider {
    private static final int TOKEN_SECRET_LENGTH = 25;

    private final TokenDao tokenDao;
    private final ConsumerDao consumerDao;

    /**
     * Class constructor.
     *
     * @param tokenDao the token dao
     * @param consumerDao the consumer dao
     */
    @Inject
    public PLCOAuthProvider(TokenDao tokenDao, ConsumerDao consumerDao) {
        this.tokenDao = tokenDao;
        this.consumerDao = consumerDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthConsumer getConsumer(String consumerKey) {
        return consumerDao.getByKey(consumerKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthToken newRequestToken(String consumerKey, String callbackUrl, Map<String, List<String>> attributes) {
        OAuthToken token = new OAuthToken();
        token.setConsumer(getConsumer(consumerKey));
        token.setToken(RandomStringUtils.randomAlphanumeric(TOKEN_SECRET_LENGTH));
        token.setSecret(RandomStringUtils.randomAlphanumeric(TOKEN_SECRET_LENGTH));
        MultivaluedMap<String, String> map = new MultivaluedMapImpl();
        for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
            map.put(entry.getKey(), entry.getValue() == null ? Collections.<String>emptyList()
                    : Collections.unmodifiableList(new ArrayList<String>(entry.getValue())));
        }
        token.setAttributes(map);
        tokenDao.save(token);
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthToken getRequestToken(String token) {
        return tokenDao.getByToken(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthToken newAccessToken(com.sun.jersey.oauth.server.spi.OAuthToken requestToken, String verifier) {
        OAuthToken token = tokenDao.getByToken(requestToken.getToken());
        if (token == null) {
            return null;
        }
        token.setToken(RandomStringUtils.randomAlphanumeric(TOKEN_SECRET_LENGTH));
        token.setSecret(RandomStringUtils.randomAlphanumeric(TOKEN_SECRET_LENGTH));
        token.setAttributes(new MultivaluedMapImpl());
        tokenDao.save(token);
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OAuthToken getAccessToken(String token) {
        OAuthToken t = tokenDao.getByToken(token);
        return t.isAuthorized() ? t : null;
    }
}
