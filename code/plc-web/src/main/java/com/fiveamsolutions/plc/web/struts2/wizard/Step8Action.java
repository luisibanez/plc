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
package com.fiveamsolutions.plc.web.struts2.wizard;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class Step8Action extends ConsentWizardAction {
    private static final long serialVersionUID = 1L;
    private boolean readAndUnderstood = false;
    private boolean consented = false;
    private boolean withdrawal = false;
    private String firstName;
    private String lastName;
    private Date today;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        validateUnderstanding();
        if (today == null) {
            addFieldError("today", getText(FIELD_REQUIRED_KEY));
        }
        if (StringUtils.isEmpty(firstName)) {
            addFieldError("firstName", getText(FIELD_REQUIRED_KEY));
        }
        if (StringUtils.isEmpty(lastName)) {
            addFieldError("lastName", getText(FIELD_REQUIRED_KEY));
        }
    }

    private void validateUnderstanding() {
        if (!readAndUnderstood) {
            addFieldError("readAndUnderstood", getText(FIELD_REQUIRED_KEY));
        }
        if (!consented) {
            addFieldError("consented", getText(FIELD_REQUIRED_KEY));
        }
        if (!withdrawal) {
            addFieldError("withdrawal", getText(FIELD_REQUIRED_KEY));
        }
    }

    /**
     * @return the readAndUnderstood
     */
    public boolean isReadAndUnderstood() {
        return readAndUnderstood;
    }

    /**
     * @param readAndUnderstood the readAndUnderstood to set
     */
    public void setReadAndUnderstood(boolean readAndUnderstood) {
        this.readAndUnderstood = readAndUnderstood;
    }

    /**
     * @return the consented
     */
    public boolean isConsented() {
        return consented;
    }

    /**
     * @param consented the consented to set
     */
    public void setConsented(boolean consented) {
        this.consented = consented;
    }

    /**
     * @return the withdrawal
     */
    public boolean isWithdrawal() {
        return withdrawal;
    }

    /**
     * @param withdrawal the withdrawal to set
     */
    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the today
     */
    public Date getToday() {
        return today;
    }

    /**
     * @param today the today to set
     */
    public void setToday(Date today) {
        this.today = today;
    }
}
