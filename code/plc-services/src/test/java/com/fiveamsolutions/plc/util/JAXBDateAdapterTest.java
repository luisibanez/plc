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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class JAXBDateAdapterTest {

    /**
     * Internal class for testing.
     *
     * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
     */
    @XmlRootElement(name = "test")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class ATestClass {
        @XmlJavaTypeAdapter(JAXBDateAdapter.class)
        private Date date;

        /**
         * @return the date
         */
        Date getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        void setDate(Date date) {
            this.date = date;
        }

    }

    /**
     * Test marshalling a null date.
     * @throws JAXBException if marshalling fails.
     */
    @Test
    public void testMarshallingNullDate() throws JAXBException {
        ATestClass testObject = new ATestClass();
        String stringifiedTestObject = marshall(testObject);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><test/>", stringifiedTestObject);
    }

    /**
     * Test marshalling a valid date.
     * @throws JAXBException if marshalling fails.
     */
    @Test
    public void testMarshallingValidDate() throws JAXBException {
        ATestClass testObject = new ATestClass();
        Date d = new SimpleDateFormat("yyyyMMdd").parse("20120101", new ParsePosition(0));
        testObject.setDate(d);
        String stringifiedTestObject = marshall(testObject);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<test><date>20120101</date></test>", stringifiedTestObject);
    }

    /**
     * Test unmarshalling a null date.
     * @throws JAXBException if unmarshalling fails.
     */
    @Test
    public void testUnmarshallingNullDate() throws JAXBException {
        String stringifiedTestObject = "<test><date></date></test>";
        ATestClass testObject = unmarshall(stringifiedTestObject);
        assertNull(testObject.getDate());
    }

    /**
     * Test unmarshalling an invalid date.
     * @throws JAXBException if unmarshalling fails.
     */
    @Test
    public void testUnmarshallingInvalidDate() throws JAXBException {
        String stringifiedTestObject = "<test><date>as2012-01-01 </date></test>";
        ATestClass testObject = unmarshall(stringifiedTestObject);
        assertNull(testObject.getDate());
    }

    /**
     * Test unmarshalling a valid date.
     * @throws JAXBException if unmarshalling fails.
     */
    @Test
    public void testUnmarshallingValidDate() throws JAXBException {
        String stringifiedTestObject = "<test></test>";
        ATestClass testObject = unmarshall(stringifiedTestObject);
        assertNull(testObject.getDate());
    }

    private String marshall(ATestClass t) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ATestClass.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(t, writer);
        return writer.toString();
    }

    private ATestClass unmarshall(String s) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ATestClass.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ATestClass) unmarshaller.unmarshal(new StreamSource(new StringReader(s)));
    }
}
