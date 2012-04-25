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
public class Step4Action extends ConsentWizardAction {
    private static final long serialVersionUID = 1L;
    private boolean researchPermission = false;
    private boolean redistributePermission = false;
    private boolean publishPermission = false;
    private boolean commercializePermission = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        super.validate();
        if (!researchPermission) {
            addFieldError("researchPermission", getText(FIELD_REQUIRED_KEY));
        }
        if (!redistributePermission) {
            addFieldError("redistributePermission", getText(FIELD_REQUIRED_KEY));
        }
        if (!publishPermission) {
            addFieldError("publishPermission", getText(FIELD_REQUIRED_KEY));
        }
        if (!commercializePermission) {
            addFieldError("commercializePermission", getText(FIELD_REQUIRED_KEY));
        }
    }

    /**
     * @return the researchPermission
     */
    public boolean isResearchPermission() {
        return researchPermission;
    }

    /**
     * @param researchPermission the researchPermission to set
     */
    public void setResearchPermission(boolean researchPermission) {
        this.researchPermission = researchPermission;
    }

    /**
     * @return the redistributePermission
     */
    public boolean isRedistributePermission() {
        return redistributePermission;
    }

    /**
     * @param redistributePermission the redistributePermission to set
     */
    public void setRedistributePermission(boolean redistributePermission) {
        this.redistributePermission = redistributePermission;
    }

    /**
     * @return the publishPermission
     */
    public boolean isPublishPermission() {
        return publishPermission;
    }

    /**
     * @param publishPermission the publishPermission to set
     */
    public void setPublishPermission(boolean publishPermission) {
        this.publishPermission = publishPermission;
    }

    /**
     * @return the commercializePermission
     */
    public boolean isCommercializePermission() {
        return commercializePermission;
    }

    /**
     * @param commercializePermission the commercializePermission to set
     */
    public void setCommercializePermission(boolean commercializePermission) {
        this.commercializePermission = commercializePermission;
    }
}
