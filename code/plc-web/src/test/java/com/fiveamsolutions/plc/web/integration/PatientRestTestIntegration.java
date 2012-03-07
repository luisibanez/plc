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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.fiveamsolutions.plc.data.transfer.Patient;
import com.sun.jersey.api.client.WebResource;

/**
 * Integration test for the patient REST inteface.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class PatientRestTestIntegration extends AbstractRestTestIntegration {
    private static final int RESULT_LENGTH = 64;

    /**
     * Tests registration of a patient via REST.
     */
    @Test
    public void registerPatient() {
        WebResource resource = getWebResource("patient");
        Patient p = new Patient();
        p.setUsername("testuser");
        p.setEmail("testUser@example.com");
        p.setPassword("password");
        p.setBirthDate(DateUtils.addYears(new Date(), -30));
        p.setFirstName("Test");
        p.setBirthName("User");
        p.setBirthCountry("USA");
        p.setBirthPlace("Rockville, Maryland");
        String result =
                resource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON_TYPE).post(String.class, p);
        assertNotNull(result);
        assertEquals(RESULT_LENGTH, result.length());
    }
}
