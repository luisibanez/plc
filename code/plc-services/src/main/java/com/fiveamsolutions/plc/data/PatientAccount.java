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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fiveamsolutions.plc.data.transfer.Patient;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@Entity(name = "patient_account")
public class PatientAccount implements PLCEntity {
    private static final long serialVersionUID = 1L;
    private static final int GUID_LENGTH = 64;

    private Long id;
    private String guid;
    private PLCUser plcUser = new PLCUser();
    private List<ChallengeQuestion> challengeQuestions = new ArrayList<ChallengeQuestion>();
    @Valid
    private PatientDemographics patientDemographics = new PatientDemographics();
    private List<PatientData> patientData = new ArrayList<PatientData>();
    private boolean surveyTaken = false;

    /**
     * Default Constructor.
     */
    public PatientAccount() {
        //Intentionally left blank
    }

    /**
     * Copy constructor from transfer object.
     * @param patient the transfer patient
     */
    public PatientAccount(Patient patient) {
        this.getPlcUser().setFullName(patient.getFullName());
        this.getPlcUser().setUsername(patient.getUsername());
        this.getPlcUser().setEmail(patient.getEmail());
        this.getPlcUser().setPassword(patient.getPassword());
        this.challengeQuestions = patient.getChallengeQuestions();
        this.getPatientDemographics().setFirstName(patient.getFirstName());
        this.getPatientDemographics().setBirthName(patient.getBirthName());
        this.getPatientDemographics().setBirthDate(patient.getBirthDate());
        this.getPatientDemographics().setBirthPlace(patient.getBirthPlace());
        this.getPatientDemographics().setBirthCountry(patient.getBirthCountry());
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
     * @return the guid
     */
    @Column(name = "guid", nullable = false, unique = true, updatable = false, length = GUID_LENGTH)
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
     * @return the plcUser
     */
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "plc_user_id")
    public PLCUser getPlcUser() {
        return plcUser;
    }

    /**
     * @param plcUser the plcUser to set
     */
    public void setPlcUser(PLCUser plcUser) {
        this.plcUser = plcUser;
    }

    /**
     * @return the challengeQuestions
     */
    @ElementCollection
    @CollectionTable(name = "challenge_questions", joinColumns = @JoinColumn(name = "patient_account_id"))
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
     * @return the patientDemographics
     */
    @OneToOne(optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_demographics_id")
    public PatientDemographics getPatientDemographics() {
        return patientDemographics;
    }

    /**
     * @param patientDemographics the patientDemographics to set
     */
    public void setPatientDemographics(PatientDemographics patientDemographics) {
        this.patientDemographics = patientDemographics;
    }

    /**
     * @return the patientData
     */
    @OneToMany(mappedBy = "patientAccount", orphanRemoval = true)
    public List<PatientData> getPatientData() {
        return patientData;
    }

    /**
     * @param patientData the patientData to set
     */
    public void setPatientData(List<PatientData> patientData) {
        this.patientData = patientData;
    }

    /**
     * @return the surveyTaken
     */
    @NotNull
    @Column(name = "survey_taken", nullable = false)
    public boolean isSurveyTaken() {
        return surveyTaken;
    }

    /**
     * @param surveyTaken the surveyTaken to set
     */
    public void setSurveyTaken(boolean surveyTaken) {
        this.surveyTaken = surveyTaken;
    }

}
