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
package com.fiveamsolutions.plc.web.inject;

import java.util.HashMap;
import java.util.Map;

import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Module for configure REST-ful jersey servlet.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PLCRestServletModule extends JerseyServletModule {
    private static final String REST_REQUESTS = "/rest/*";
    private static final String REST_REQUEST_PACKAGE_KEY = "plc.rest.package";
    private static final String REST_REQUEST_FILTERS_KEY = "plc.rest.request.filters";
    private static final String REST_CONTAINER_FILTERS = "plc.rest.container.filters";
    private final PLCApplicationResources appResources;

    /**
     * Class constructor.
     * @param appResouces the application resources
     */
    public PLCRestServletModule(PLCApplicationResources appResouces) {
        this.appResources = appResouces;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configureServlets() {
        super.configureServlets();
        Map<String, String> params = new HashMap<String, String>();
        params.put(PackagesResourceConfig.PROPERTY_PACKAGES, appResources.getStringResource(REST_REQUEST_PACKAGE_KEY));
        params.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        params.put(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES,
                appResources.getStringResource(REST_REQUEST_FILTERS_KEY));
        params.put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                appResources.getStringResource(REST_CONTAINER_FILTERS));
        serve(REST_REQUESTS).with(GuiceContainer.class, params);
    }
}
