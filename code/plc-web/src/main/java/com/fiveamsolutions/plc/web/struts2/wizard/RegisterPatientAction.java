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
package com.fiveamsolutions.plc.web.struts2.wizard;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;

import com.fiveamsolutions.plc.data.ChallengeQuestion;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.service.PatientInformationService;
import com.google.inject.Inject;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class RegisterPatientAction extends ConsentWizardAction implements Preparable {
    private static final long serialVersionUID = 1L;
    private final PatientInformationService patientService;
    @Valid
    private PatientAccount patientAccount = new PatientAccount();
    private String repeatPassword;
    private String repeatEmail;
    private String challengeQuestion;
    private String challengeAnswer;
    private String fullName;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        patientAccount.setPatientDemographics((PatientDemographics) getSession().get("patientDemographics"));
    }

    /**
     * Class constructor.
     *
     * @param patientService the patient service
     */
    @Inject
    public RegisterPatientAction(PatientInformationService patientService) {
        this.patientService = patientService;
    }

    /**
     * Registers the given patient account.
     * @return the struts forwarding result
     */
    public String register() {
        ChallengeQuestion qa = new ChallengeQuestion();
        qa.setQuestion(challengeQuestion);
        qa.setAnswer(challengeAnswer);
        patientAccount.getChallengeQuestions().add(qa);
        patientService.registerPatient(patientAccount);
        return SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        if (!StringUtils.equals(patientAccount.getPassword(), getRepeatPassword())) {
            addFieldError("patientAccount.password", "Passwords must match.");
            addFieldError("repeatPassword", "Passwords must match.");
        }
        if (!StringUtils.equals(patientAccount.getPassword(), getRepeatPassword())) {
            addFieldError("patientAccount.email", "Email Addresses must match.");
            addFieldError("repeatEmail", "Email Addresses must match.");
        }
        if (StringUtils.isEmpty(fullName)) {
            addFieldError("fullName", getText(FIELD_REQUIRED_KEY));
        }
        if (StringUtils.isEmpty(challengeQuestion)) {
            addFieldError("challengeQuestion", getText(FIELD_REQUIRED_KEY));
        }
        if (StringUtils.isEmpty(challengeAnswer)) {
            addFieldError("challengeAnswer", getText(FIELD_REQUIRED_KEY));
        }
    }

    /**
     * @return the patientAccount
     */
    public PatientAccount getPatientAccount() {
        return patientAccount;
    }

    /**
     * @param patientAccount the patientAccount to set
     */
    public void setPatientAccount(PatientAccount patientAccount) {
        this.patientAccount = patientAccount;
    }

    /**
     * @return the repeatPassword
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * @param repeatPassword the repeatPassword to set
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * @return the repeatEmail
     */
    public String getRepeatEmail() {
        return repeatEmail;
    }

    /**
     * @param repeatEmail the repeatEmail to set
     */
    public void setRepeatEmail(String repeatEmail) {
        this.repeatEmail = repeatEmail;
    }

    /**
     * @return the challegeQuestion
     */
    public String getChallengeQuestion() {
        return challengeQuestion;
    }

    /**
     * @param challengeQuestion the challengeQuestion to set
     */
    public void setChallengeQuestion(String challengeQuestion) {
        this.challengeQuestion = challengeQuestion;
    }

    /**
     * @return the challengeAnswer
     */
    public String getChallengeAnswer() {
        return challengeAnswer;
    }

    /**
     * @param challengeAnswer the challengeAnswer to set
     */
    public void setChallengeAnswer(String challengeAnswer) {
        this.challengeAnswer = challengeAnswer;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
