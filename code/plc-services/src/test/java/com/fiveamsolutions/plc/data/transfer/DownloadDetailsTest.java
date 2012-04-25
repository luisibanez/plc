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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
public class DownloadDetailsTest {

    /**
     * Tests JSON marshalling.
     * @throws JAXBException on error
     */
    @Test
    public void jsonMarshalling() throws JAXBException {
        JAXBContext context = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(),
                DownloadDetails.class);
        JSONMarshaller marshaller = JSONJAXBContext.getJSONMarshaller(context.createMarshaller());

        StringWriter writer = new StringWriter();
        DownloadDetails downloadDetails = TestPLCEntityFactory.createDownloadDetails();
        marshaller.marshallToJSON(downloadDetails, writer);
        assertEquals(getJson(downloadDetails),  writer.toString());
    }

    private String getJson(DownloadDetails downloadDetails) {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.getDefault());
        String dateString  = dateFormat.format(downloadDetails.getExpirationDate());
        StringBuilder builder = new StringBuilder();
        builder.append("{").append("\n");
        builder.append("  \"size\" : ").append(downloadDetails.getSize()).append(",\n");
        builder.append("  \"unit\" : \"").append(downloadDetails.getUnit()).append("\",\n");
        builder.append("  \"url\" : \"").append(downloadDetails.getUrl()).append("\",\n");
        builder.append("  \"expirationDate\" : \"").append(dateString).append("\"\n");
        builder.append("}");
        return builder.toString();
    }
}
