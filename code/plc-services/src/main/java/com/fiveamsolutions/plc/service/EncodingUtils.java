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
package com.fiveamsolutions.plc.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.util.PLCApplicationResources;

/**
 * Utilities for hashing/encoding passwords and generating guids.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class EncodingUtils {
    private static final Logger LOG = Logger.getLogger(EncodingUtils.class);
    private static final String DATE_FORMAT_KEY = "hashing.date.format";
    private static final String HASHING_ALGORITHM_KEY = "hashing.algorithm";
    private static final String ENCODING_KEY = "hashing.string.encoding";

    private final PLCApplicationResources appResources;
    private final MessageDigest digester;

    /**
     * Class constructor.
     * @param appResources the application resources
     * @throws NoSuchAlgorithmException if the hashing algorithm doesn't exist
     */
    public EncodingUtils(PLCApplicationResources appResources) throws NoSuchAlgorithmException {
        this.appResources = appResources;
        digester = MessageDigest.getInstance(this.appResources.getStringResource(HASHING_ALGORITHM_KEY));
    }

    /**
     * Generates the patient's GUID from the given patient data.
     * @param patientDemographics the patient demographics to generate the GUID from
     * @return the generated GUID
     */
    public String generatePatientGUID(PatientDemographics patientDemographics) {
        SimpleDateFormat sdf =
                new SimpleDateFormat(appResources.getStringResource(DATE_FORMAT_KEY), Locale.getDefault());
        String dob = sdf.format(patientDemographics.getBirthDate());
        StringBuilder builder = new StringBuilder().append(patientDemographics.getFirstName()).append("_")
                .append(patientDemographics.getBirthName()).append("_").append(dob).append("_")
                .append(patientDemographics.getBirthPlace()).append("_")
                .append(patientDemographics.getBirthCountry());
        String stringToHash = StringUtils.strip(builder.toString().replace("\\s+", " "));
        return hashString(stringToHash);
    }

    /**
     * Hashes the given string, encoding it as a hex string.
     * @param input the string to encode
     * @return the hashed and encoded string
     */
    public String hashString(String input) {
        byte[] hashedBytes = {};
        digester.reset();
        try {
            hashedBytes = digester.digest(input.getBytes(appResources.getStringResource(ENCODING_KEY)));
        } catch (UnsupportedEncodingException e) {
            LOG.error("Unable to retrieve bytes in UTF-8 format.", e);
        }
        return Hex.encodeHexString(hashedBytes);
    }
}
