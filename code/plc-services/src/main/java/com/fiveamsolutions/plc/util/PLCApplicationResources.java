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
package com.fiveamsolutions.plc.util;

import java.util.ResourceBundle;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.google.inject.Inject;

/**
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PLCApplicationResources {
    private static final Logger LOG = Logger.getLogger(PLCApplicationResources.class);
    private final ResourceBundle resourceBundle;

    /**
     * Class constructor.
     * @param resourceBundle application's resource bundle.
     */
    @Inject
    public PLCApplicationResources(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * Retrieve the specified string resource.
     * @param resourceName the name of the resource.
     * @return the resource value.
     */
    public String getStringResource(String resourceName) {
        return resourceBundle.getString(resourceName);
    }

    /**
     * Retrieves the specified resource as an integer.
     * @param resourceName the name of the resource
     * @return the resource value as an int
     */
    public int getIntResource(String resourceName) {
        String value = getStringResource(resourceName);
        Integer i = 0;
        try {
            i = NumberUtils.createInteger(value);
        } catch (NumberFormatException e) {
            LOG.error("Error converting " + value  + " to int.");
        }
        return i;
    }
}
