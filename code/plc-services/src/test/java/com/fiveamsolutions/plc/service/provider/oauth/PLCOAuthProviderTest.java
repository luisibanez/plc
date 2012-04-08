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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.dao.oauth.ConsumerDao;
import com.fiveamsolutions.plc.dao.oauth.TokenDao;
import com.fiveamsolutions.plc.data.oauth.Consumer;
import com.fiveamsolutions.plc.service.provider.oauth.PLCOAuthProvider;
import com.sun.jersey.oauth.server.spi.OAuthConsumer;
import com.sun.jersey.oauth.server.spi.OAuthToken;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PLCOAuthProviderTest {
    private PLCOAuthProvider provider;
    private TokenDao tokenDao;
    private Consumer consumer;
    private com.fiveamsolutions.plc.data.oauth.OAuthToken token;

    /**
     * Prepares test data.
     */
    @Before
    public void prepareTestData() {
        consumer = TestPLCEntityFactory.createConsumer();
        token = TestPLCEntityFactory.createToken();

        tokenDao = mock(TokenDao.class);
        when(tokenDao.getByToken(anyString())).thenReturn(token);
        ConsumerDao consumerDao = mock(ConsumerDao.class);
        when(consumerDao.getByKey(anyString())).thenReturn(consumer);
        provider = new PLCOAuthProvider(tokenDao, consumerDao);
    }

    /**
     * Tests retrieving consumer by key.
     */
    @Test
    public void getConsumer() {
        OAuthConsumer c = provider.getConsumer(consumer.getKey());
        assertEquals(consumer.getKey(), c.getKey());
        assertEquals(consumer.getSecret(), c.getSecret());
    }

    /**
     * Retrieves getting a new request token.
     */
    @Test
    public void newRequestToken() {
        Map<String, List<String>> attr = new HashMap<String, List<String>>();
        attr.put("foo", Arrays.asList("bar", "baz"));
        attr.put("qux", null);
        OAuthToken token = provider.newRequestToken(consumer.getKey(), "", attr);
        assertNotNull(token);
        assertNotNull(token.getConsumer());
        assertNotNull(token.getSecret());
        assertNotNull(token.getToken());
        assertNotNull(token.getAttributes());
    }

    /**
     * Retrieves getting a new access token.
     */
    @Test
    public void newAccessToken() {
        OAuthToken t = provider.newAccessToken(token, "");
        assertNotNull(t);
        assertNotNull(t.getConsumer());
        assertNotNull(t.getSecret());
        assertNotNull(t.getToken());
        assertNotNull(t.getAttributes());
    }

    /**
     * Retrieves getting a new access token without a proper request token.
     */
    @Test
    public void newAccessTokenNoRequestToken() {
        when(tokenDao.getByToken(anyString())).thenReturn(null);
        OAuthToken t = provider.newAccessToken(token, "");
        assertNull(t);
    }

    /**
     * Retrieve an existing request token.
     */
    @Test
    public void getRequestToken() {
        com.fiveamsolutions.plc.data.oauth.OAuthToken t = provider.getRequestToken(token.getToken());
        assertNotNull(t);
    }

    /**
     * Retrieve an existing access token.
     */
    @Test
    public void getAccessToken() {
        com.fiveamsolutions.plc.data.oauth.OAuthToken t = provider.getAccessToken(token.getToken());
        assertNull(t);

        token.setAuthorized(true);
        t = provider.getAccessToken(token.getToken());
        assertNotNull(t);

    }

}
