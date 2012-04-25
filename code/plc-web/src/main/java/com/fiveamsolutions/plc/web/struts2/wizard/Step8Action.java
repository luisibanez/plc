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
    private boolean agreement = false;
    private String fullName;
    private Date today;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        if (!readAndUnderstood) {
            addFieldError("readAndUnderstood", getText(FIELD_REQUIRED_KEY));
        }
        if (!agreement) {
            addFieldError("agreement", getText(FIELD_REQUIRED_KEY));
        }
        if (today == null) {
            addFieldError("today", getText(FIELD_REQUIRED_KEY));
        }
        if (StringUtils.isEmpty(fullName)) {
            addFieldError("fullName", getText(FIELD_REQUIRED_KEY));
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
     * @return the agreement
     */
    public boolean isAgreement() {
        return agreement;
    }

    /**
     * @param agreement the agreement to set
     */
    public void setAgreement(boolean agreement) {
        this.agreement = agreement;
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
