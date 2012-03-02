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
package com.fiveamsolutions.plc.web.struts2.controllers;

import java.util.Collection;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.fiveamsolutions.plc.dao.PatientAccountDao;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.google.inject.Inject;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@ParentPackage("protected")
@Namespace("/protected/patient")
public class PatientAccountController extends PLCRestController<PatientAccount> {
    private static final long serialVersionUID = 1L;
    private final PatientAccountDao patientAccountDao;
    private PatientAccount model;
    private Collection<PatientAccount> patientAccounts;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws Exception {
        if (getId() != null) {
            model = patientAccountDao.getById(getId());
        }
    }

    /**
     * Class constructor.
     *
     * @param patientAccountDao the patient account dao
     */
    @Inject
    public PatientAccountController(PatientAccountDao patientAccountDao) {
        this.patientAccountDao = patientAccountDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpHeaders show() {
        model = patientAccountDao.getById(getId());
        return new DefaultHttpHeaders("show");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpHeaders index() {
        patientAccounts = patientAccountDao.getAll();
        return new DefaultHttpHeaders("index").disableCaching();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() {
        return "edit";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNew() {
        model = new PatientAccount();
        return "editNew";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String destroy() {
        patientAccountDao.deleteById(getId());
        return "success";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpHeaders create() {
        patientAccountDao.save(model);
        return new DefaultHttpHeaders("success").setLocationId(model.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() {
        patientAccountDao.save(model);
        return "success";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getModel() {
        return patientAccounts != null ? patientAccounts : model;
    }
}
