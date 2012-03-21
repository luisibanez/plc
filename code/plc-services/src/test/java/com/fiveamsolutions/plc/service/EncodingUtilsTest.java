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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.util.TestApplicationResourcesFactory;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class EncodingUtilsTest {
    private static final int EXPECTED_HASHED_LENGTH = 64;
    private EncodingUtils encodingUtils;

    /**
     * Prepares the test.
     */
    @Before
    public void prepareTest() throws Exception {
        encodingUtils = new EncodingUtils(TestApplicationResourcesFactory.getApplicationResources());
    }

    /**
     * Tests patient GUID generation.
     */
    @Test
    public void generatePatientGUID() {
        PatientDemographics patientDemographics = TestPLCEntityFactory.createPatientDemographics();
        String guid = encodingUtils.generatePatientGUID(patientDemographics);

        assertTrue(StringUtils.isNotEmpty(guid));
        assertEquals(EXPECTED_HASHED_LENGTH, guid.length());
        assertEquals(guid, encodingUtils.generatePatientGUID(patientDemographics));

        PatientDemographics differentPatientData = TestPLCEntityFactory.createPatientDemographics();
        differentPatientData.setBirthDate(DateUtils.addDays(new Date(), 1));
        String differentGuid = encodingUtils.generatePatientGUID(differentPatientData);
        assertTrue(StringUtils.isNotEmpty(differentGuid));
        assertEquals(EXPECTED_HASHED_LENGTH, differentGuid.length());
        assertFalse(StringUtils.equals(guid, differentGuid));
    }

    /**
     * Tests hashing of string.
     */
    public void hashString() {
        String hashed = encodingUtils.hashString("Hash Me");
        assertEquals(EXPECTED_HASHED_LENGTH, hashed);
    }
}
