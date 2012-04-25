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
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fiveamsolutions.plc.data.enums.FileSizeUnit;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@XmlRootElement(name = "summary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Summary", propOrder = { "totalFileCount", "filteredFileCount", "totalPGUIDCount", "filteredPGUIDCount",
        "totalFileSize", "totalFileSizeUnit", "filteredFileSize", "filteredFileSizeUnit", "fileCountPerTag",
        "filteredFileCountPerTag"
})
public class Summary implements Serializable {
    private static final long serialVersionUID = 1L;
    private long totalFileCount;
    private long filteredFileCount;
    private long totalPGUIDCount;
    private long filteredPGUIDCount;
    private long totalFileSize;
    private FileSizeUnit totalFileSizeUnit = FileSizeUnit.B;
    private long filteredFileSize;
    private FileSizeUnit filteredFileSizeUnit = FileSizeUnit.B;
    private Map<String, Integer> fileCountPerTag;
    private Map<String, Integer> filteredFileCountPerTag;

    /**
     * @return the totalFileCount
     */
    public long getTotalFileCount() {
        return totalFileCount;
    }

    /**
     * @param totalFileCount the totalFileCount to set
     */
    public void setTotalFileCount(long totalFileCount) {
        this.totalFileCount = totalFileCount;
    }

    /**
     * @return the filteredFileCount
     */
    public long getFilteredFileCount() {
        return filteredFileCount;
    }

    /**
     * @param filteredFileCount the filteredFileCount to set
     */
    public void setFilteredFileCount(long filteredFileCount) {
        this.filteredFileCount = filteredFileCount;
    }

    /**
     * @return the totalPGUIDCount
     */
    public long getTotalPGUIDCount() {
        return totalPGUIDCount;
    }

    /**
     * @param totalPGUIDCount the totalPGUIDCount to set
     */
    public void setTotalPGUIDCount(long totalPGUIDCount) {
        this.totalPGUIDCount = totalPGUIDCount;
    }

    /**
     * @return the filteredPGUIDCount
     */
    public long getFilteredPGUIDCount() {
        return filteredPGUIDCount;
    }

    /**
     * @param filteredPGUIDCount the filteredPGUIDCount to set
     */
    public void setFilteredPGUIDCount(long filteredPGUIDCount) {
        this.filteredPGUIDCount = filteredPGUIDCount;
    }

    /**
     * @return the totalFileSize
     */
    public long getTotalFileSize() {
        return totalFileSize;
    }

    /**
     * @param size the totalFileSize to set
     */
    public void setTotalFileSize(long size) {
        this.totalFileSize = size;
    }

    /**
     * @return the totalFileSizeUnit
     */
    public FileSizeUnit getTotalFileSizeUnit() {
        return totalFileSizeUnit;
    }

    /**
     * @param totalFileSizeUnit the totalFileSizeUnit to set
     */
    public void setTotalFileSizeUnit(FileSizeUnit totalFileSizeUnit) {
        this.totalFileSizeUnit = totalFileSizeUnit;
    }

    /**
     * @return the filteredFileSize
     */
    public long getFilteredFileSize() {
        return filteredFileSize;
    }

    /**
     * @param filteredFileSize the filteredFileSize to set
     */
    public void setFilteredFileSize(long filteredFileSize) {
        this.filteredFileSize = filteredFileSize;
    }

    /**
     * @return the filteredFileSizeUnit
     */
    public FileSizeUnit getFilteredFileSizeUnit() {
        return filteredFileSizeUnit;
    }

    /**
     * @param unit the filteredFileSizeUnit to set
     */
    public void setFilteredFileSizeUnit(FileSizeUnit unit) {
        this.filteredFileSizeUnit = unit;
    }

    /**
     * @return the fileCountPerTag
     */
    public Map<String, Integer> getFileCountPerTag() {
        return fileCountPerTag;
    }

    /**
     * @param fileCountPerTag the fileCountPerTag to set
     */
    public void setFileCountPerTag(Map<String, Integer> fileCountPerTag) {
        this.fileCountPerTag = fileCountPerTag;
    }

    /**
     * @return the filterFileCountPerTag
     */
    public Map<String, Integer> getFilteredFileCountPerTag() {
        return filteredFileCountPerTag;
    }

    /**
     * @param countPerTag the countPerTag to set
     */
    public void setFilteredFileCountPerTag(Map<String, Integer> countPerTag) {
        this.filteredFileCountPerTag = countPerTag;
    }
}
