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


/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class Step7Action extends ConsentWizardAction {
    private static final long serialVersionUID = 1L;

    private boolean affirmUncertainty = false;
    private boolean affirmConsent = false;
    private boolean affirmWithdrawlPolicy = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        if (!affirmUncertainty) {
            addFieldError("affirmUncertainty", getText(FIELD_REQUIRED_KEY));
        }
        if (!affirmConsent) {
            addFieldError("affirmConsent", getText(FIELD_REQUIRED_KEY));
        }
        if (!affirmWithdrawlPolicy) {
            addFieldError("affirmWithdrawlPolicy", getText(FIELD_REQUIRED_KEY));
        }
    }

    /**
     * @return the affirmUncertainty
     */
    public boolean isAffirmUncertainty() {
        return affirmUncertainty;
    }

    /**
     * @param affirmUncertainty the affirmUncertainty to set
     */
    public void setAffirmUncertainty(boolean affirmUncertainty) {
        this.affirmUncertainty = affirmUncertainty;
    }

    /**
     * @return the affirmConsent
     */
    public boolean isAffirmConsent() {
        return affirmConsent;
    }

    /**
     * @param affirmConsent the affirmConsent to set
     */
    public void setAffirmConsent(boolean affirmConsent) {
        this.affirmConsent = affirmConsent;
    }

    /**
     * @return the affirmWithdrawlPolicy
     */
    public boolean isAffirmWithdrawlPolicy() {
        return affirmWithdrawlPolicy;
    }

    /**
     * @param affirmWithdrawlPolicy the affirmWithdrawlPolicy to set
     */
    public void setAffirmWithdrawlPolicy(boolean affirmWithdrawlPolicy) {
        this.affirmWithdrawlPolicy = affirmWithdrawlPolicy;
    }
}
