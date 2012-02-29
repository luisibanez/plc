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
package com.fiveamsolutions.plc.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.fiveamsolutions.plc.data.PatientData;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientInformationServiceBean implements PatientInformationService {
    private static final Logger LOG = Logger.getLogger(PatientInformationServiceBean.class);
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String HASHING_ALGORITHM = "SHA-256";
    private final MessageDigest digester;

    /**
     * Class constructor.
     * @throws NoSuchAlgorithmException if the SHA-256 hashing algorithm doesn't exist
     */
    public PatientInformationServiceBean() throws NoSuchAlgorithmException {
        digester = MessageDigest.getInstance(HASHING_ALGORITHM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generatePatientGUID(PatientData patientData) {
        String dob = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(patientData.getBirthDate());
        StringBuilder builder = new StringBuilder().append(patientData.getFirstName())
                .append(patientData.getBirthName()).append(dob).append(patientData.getBirthPlace())
                .append(patientData.getBirthCountry());
        digester.reset();
        byte[] bytes = {};
        try {
            bytes = builder.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Unable to retrieve bytes in UTF-8 format.", e);
        }
        return Hex.encodeHexString(digester.digest(bytes));
    }
}
