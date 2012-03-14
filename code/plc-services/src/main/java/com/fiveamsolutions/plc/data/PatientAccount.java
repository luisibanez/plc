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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fiveamsolutions.plc.data.transfer.Patient;
import com.fiveamsolutions.plc.data.validator.UniqueUsername;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@UniqueUsername
@Entity(name = "patient_account")
public class PatientAccount implements PLCEntity {
    private static final long serialVersionUID = 1L;
    private static final int SALT_LENGTH = 16;
    private static final int GUID_LENGTH = 64;
    private static final int MIN_USERNAME_LENGTH = 6;
    private static final int MAX_USERNAME_LENGTH = 20;

    private Long id;
    private String email;
    private String username;
    private String password;
    private String salt;
    private String guid;
    private List<ChallengeQuestion> challengeQuestions = new ArrayList<ChallengeQuestion>();
    @Valid
    private PatientData patientData = new PatientData();

    /**
     * Default Constructor.
     */
    public PatientAccount() {
        salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
    }

    /**
     * Copy constructor from transfer object.
     * @param patient the transfer patient
     */
    public PatientAccount(Patient patient) {
        this.salt = RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
        this.username = patient.getUsername();
        this.email = patient.getEmail();
        this.password = patient.getPassword();
        this.challengeQuestions = patient.getChallengeQuestions();
        this.getPatientData().setFirstName(patient.getFirstName());
        this.getPatientData().setBirthName(patient.getBirthName());
        this.getPatientData().setBirthDate(patient.getBirthDate());
        this.getPatientData().setBirthPlace(patient.getBirthPlace());
        this.getPatientData().setBirthCountry(patient.getBirthCountry());
    }

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
     * @return the email
     */
    @NotEmpty
    @Email
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    @Length(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    @NotEmpty
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the salt
     */
    @Column(name = "salt", nullable = false, updatable = false, length = SALT_LENGTH)
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the guid
     */
    @Column(name = "guid", nullable = false, updatable = false, length = GUID_LENGTH)
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return the challengeQuestions
     */
    @ElementCollection
    @CollectionTable(name = "challenge_questions", joinColumns = @JoinColumn(name = "patient_data_id"))
    public List<ChallengeQuestion> getChallengeQuestions() {
        return challengeQuestions;
    }

    /**
     * @param challengeQuestions the challengeQuestions to set
     */
    public void setChallengeQuestions(List<ChallengeQuestion> challengeQuestions) {
        this.challengeQuestions = challengeQuestions;
    }

    /**
     * @return the patientData
     */
    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_data_id")
    public PatientData getPatientData() {
        return patientData;
    }

    /**
     * @param patientData the patientData to set
     */
    public void setPatientData(PatientData patientData) {
        this.patientData = patientData;
    }

}
