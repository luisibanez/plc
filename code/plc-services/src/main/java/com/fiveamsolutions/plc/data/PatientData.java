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
package com.fiveamsolutions.plc.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fiveamsolutions.plc.data.enums.PatientDataSource;
import com.fiveamsolutions.plc.data.enums.PatientDataType;

/**
 * Class for representing uploaded patient data.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@XmlRootElement(name = "patient_data")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientData", propOrder =  {
        "dataType", "dataSource", "version", "notes", "fileName", "fileData", "tags"
})
@Entity(name = "patient_data")
public class PatientData implements PLCEntity {
    private static final long serialVersionUID = 1L;
    @XmlTransient
    private Long id;
    private PatientDataType dataType;
    private PatientDataSource dataSource;
    private String version;
    private String notes;
    @XmlTransient
    private Date uploadedDate = new Date();
    private String fileName;
    private byte[] fileData;
    @XmlTransient
    private long fileDataSize;
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "value")
    private List<String> tags = new ArrayList<String>();
    @XmlTransient
    private PatientAccount patientAccount;


    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the tags
     */
    @ElementCollection
    @CollectionTable(name = "patient_data_tags", joinColumns = @JoinColumn(name = "patient_data_id"))
    @Column(name = "tag")
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * @return the dataType
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    public PatientDataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(PatientDataType dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the dataSource
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_source")
    public PatientDataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(PatientDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the version
     */
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the notes
     */
    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the uploadedDate
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploaded_date", nullable = false)
    public Date getUploadedDate() {
        return uploadedDate;
    }

    /**
     * @param uploadedDate the uploadedDate to set
     */
    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    /**
     * @return the fileName
     */
    @NotEmpty
    @Column(name = "file_name", nullable = false)
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileData
     */
    @NotNull
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "file_data", nullable = false)
    public byte[] getFileData() {
        return ArrayUtils.clone(fileData);
    }

    /**
     * @param fileData the fileData to set
     */
    public void setFileData(byte[] fileData) {
        this.fileData = ArrayUtils.clone(fileData);
        this.fileDataSize = fileData.length;
    }

    /**
     * The associated patient data's size in bytes.
     * @return the fileDataSize
     */
    @NotNull
    @Column(name = "file_data_size", nullable = false)
    public long getFileDataSize() {
        return fileDataSize;
    }

    /**
     * @param fileDataSize the fileDataSize to set
     */
    public void setFileDataSize(long fileDataSize) {
        this.fileDataSize = fileDataSize;
    }

    /**
     * @return the patientAccount
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_account_id")
    public PatientAccount getPatientAccount() {
        return patientAccount;
    }

    /**
     * @param patientAccount the patientAccount to set
     */
    public void setPatientAccount(PatientAccount patientAccount) {
        this.patientAccount = patientAccount;
    }
}
