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
package com.fiveamsolutions.plc.web.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public abstract class AbstractRestTestIntegration {
    private static final String CONFIG_FILE_NAME = "integration-test.properties";
    private static final Properties PROPERTIES = new Properties();

    /**
     * @return the properties
     */
    protected static Properties getProperties() {
        return PROPERTIES;
    }

    /**
     * Setup global properties.
     *
     * @throws IOException if the configuration file could not be read.
     */
    @BeforeClass
    public static void setUpGlobal() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = AbstractRestTestIntegration.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * Returns the web resource, used to make REST calls.
     * @param endpoint the endpoint to query
     * @return the web resource
     */
    protected WebResource getWebResource(String endpoint) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource r = client.resource(PROPERTIES.getProperty("test.rest.context") + "/" + endpoint);
        return r;
    }
}
