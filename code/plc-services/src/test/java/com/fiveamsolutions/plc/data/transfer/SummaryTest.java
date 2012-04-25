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
package com.fiveamsolutions.plc.data.transfer;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.api.json.JSONMarshaller;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class SummaryTest {


    /**
     * Tests JSON marshalling.
     * @throws JAXBException on error
     */
    @Test
    public void jsonMarshalling() throws JAXBException {
        JAXBContext context = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(),
                Summary.class);
        JSONMarshaller marshaller = JSONJAXBContext.getJSONMarshaller(context.createMarshaller());

        StringWriter writer = new StringWriter();
        Summary summary = TestPLCEntityFactory.createSummary();
        marshaller.marshallToJSON(summary, writer);
        assertEquals(getJson(summary),  writer.toString());
    }

    private String getJson(Summary summary) {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append("\n");
        builder.append("  \"totalFileCount\" : ").append(summary.getTotalFileCount()).append(",\n");
        builder.append("  \"filteredFileCount\" : ").append(summary.getFilteredFileCount()).append(",\n");
        builder.append("  \"totalPGUIDCount\" : ").append(summary.getTotalPGUIDCount()).append(",\n");
        builder.append("  \"filteredPGUIDCount\" : ").append(summary.getFilteredPGUIDCount()).append(",\n");
        builder.append("  \"totalFileSize\" : ").append(summary.getTotalFileSize()).append(",\n");
        builder.append("  \"totalFileSizeUnit\" : \"").append(summary.getTotalFileSizeUnit()).append("\",\n");
        builder.append("  \"filteredFileSize\" : ").append(summary.getFilteredFileSize()).append(",\n");
        builder.append("  \"filteredFileSizeUnit\" : \"").append(summary.getFilteredFileSizeUnit()).append("\",\n");
        builder.append("  \"fileCountPerTag\" : {\n");

        Iterator<Map.Entry<String, Integer>> countPerTag = summary.getFileCountPerTag().entrySet().iterator();
        while (countPerTag.hasNext()) {
            Map.Entry<String, Integer> entry = countPerTag.next();
            builder.append("    \"entry\" : {\n");
            builder.append("      \"key\" : \"").append(entry.getKey()).append("\",\n");
            builder.append("      \"value\" : \"").append(entry.getValue()).append("\"\n");
            builder.append("    }");
            if (countPerTag.hasNext()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("  },\n");
        builder.append("  \"filteredFileCountPerTag\" : {\n");
        Iterator<Map.Entry<String, Integer>> filteredCountPerTag = summary.getFilteredFileCountPerTag().entrySet().iterator();
        while (filteredCountPerTag.hasNext()) {
            Map.Entry<String, Integer> entry = filteredCountPerTag.next();
            builder.append("    \"entry\" : {\n");
            builder.append("      \"key\" : \"").append(entry.getKey()).append("\",\n");
            builder.append("      \"value\" : \"").append(entry.getValue()).append("\"\n");
            builder.append("    }");
            if (filteredCountPerTag.hasNext()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("  }\n");
        builder.append("}");
        return builder.toString();
    }
}
