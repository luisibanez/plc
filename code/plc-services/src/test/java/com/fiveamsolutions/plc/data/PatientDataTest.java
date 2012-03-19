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
package com.fiveamsolutions.plc.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONMarshaller;
import com.sun.jersey.api.json.JSONUnmarshaller;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientDataTest {


    /**
     * Tests JSON marshalling.
     * @throws Exception on error
     */
    @Test
    public void jsonMarshalling() throws Exception {
        JAXBContext context = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(),
                PatientData.class);
        JSONMarshaller marshaller = JSONJAXBContext.getJSONMarshaller(context.createMarshaller());

        StringWriter writer = new StringWriter();
        PatientData patientData = TestPLCEntityFactory.createPatientData();
        marshaller.marshallToJSON(patientData, writer);
        assertEquals(getPatientJson(patientData), writer.toString());
    }

    /**
     * Tests JSON unmarshalling.
     * @throws Exception on error
     */
    @Test
    public void jsonUnmarshalling() throws Exception {
        JAXBContext context = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(),
                PatientData.class);
        JSONUnmarshaller unmarshaller = JSONJAXBContext.getJSONUnmarshaller(context.createUnmarshaller());

        PatientData patientData = TestPLCEntityFactory.createPatientData();
        String json = getPatientJson(patientData);

        PatientData unmarshalledData = unmarshaller.unmarshalFromJSON(IOUtils.toInputStream(json), PatientData.class);
        assertNotNull(unmarshalledData);
    }

    private String getPatientJson(PatientData patient) {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append("\n");
        builder.append("  \"dataType\" : \"").append(patient.getDataType()).append("\",\n");
        builder.append("  \"dataSource\" : \"").append(patient.getDataSource()).append("\",\n");
        builder.append("  \"version\" : \"").append(patient.getVersion()).append("\",\n");
        builder.append("  \"notes\" : \"").append(patient.getNotes()).append("\",\n");
        builder.append("  \"fileName\" : \"").append(patient.getFileName()).append("\",\n");
        builder.append("  \"fileData\" : \"").append(Base64.encodeBase64String(patient.getFileData())).append("\",\n");
        builder.append("  \"tags\" : [ {\n");
        for (int i = 0; i < patient.getTags().size(); i++) {
            String tag = patient.getTags().get(i);
            builder.append("    \"value\" : \"").append(tag).append("\"");
            if (i < patient.getTags().size() - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }

        builder.append("  } ]\n");
        builder.append("}");
        return builder.toString();
    }
}
