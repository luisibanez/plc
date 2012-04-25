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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.collections.CollectionUtils;

import com.fiveamsolutions.plc.util.JAXBDateTimeAdapter;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@XmlRootElement(name = "filter")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Filter", propOrder = { "pguids", "tags", "lastChangeDate" })
public class Filter implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElementWrapper(name = "pguids")
    @XmlElement(name = "pguid")
    private Set<String> pguids = new HashSet<String>();
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private Set<String> tags = new HashSet<String>();
    @XmlJavaTypeAdapter(JAXBDateTimeAdapter.class)
    private Date lastChangeDate;

    /**
     * @return the pguids
     */
    public Set<String> getPguids() {
        return pguids;
    }

    /**
     * @param pguids the pguids to set
     */
    public void setPguids(Set<String> pguids) {
        this.pguids = pguids;
    }

    /**
     * @return the tags
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    /**
     * @return the lastChangeDate
     */
    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    /**
     * @param lastChangeDate the lastChangeDate to set
     */
    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    /**
     * Returns true iff at least one filter value has been provided.
     * @return true iff one filter value has been provided
     */
    public boolean valuesProvided() {
        return CollectionUtils.isNotEmpty(getPguids()) || CollectionUtils.isNotEmpty(getTags())
                || getLastChangeDate() != null;
    }
}
