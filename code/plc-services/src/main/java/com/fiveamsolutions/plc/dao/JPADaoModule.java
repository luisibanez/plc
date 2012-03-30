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
package com.fiveamsolutions.plc.dao;

import com.fiveamsolutions.plc.dao.oauth.ConsumerDao;
import com.fiveamsolutions.plc.dao.oauth.ConsumerJPADao;
import com.fiveamsolutions.plc.dao.oauth.TokenDao;
import com.fiveamsolutions.plc.dao.oauth.TokenJPADao;
import com.fiveamsolutions.plc.inject.PersistentServiceInitializer;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class JPADaoModule extends AbstractModule {
    private static final String PERSISTENCE_UNIT_NAME_KEY = "plc.persistenceUnit.name";
    private final PLCApplicationResources applicationResources;

    /**
     * Class constructor.
     * @param appResources the application resources
     */
    public JPADaoModule(PLCApplicationResources appResources) {
        this.applicationResources = appResources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        install(new JpaPersistModule(applicationResources.getStringResource(PERSISTENCE_UNIT_NAME_KEY)));
        bind(PatientDemographicsDao.class).to(PatientDemographicsJPADao.class);
        bind(PatientAccountDao.class).to(PatientAccountJPADao.class);
        bind(PatientDataDao.class).to(PatientDataJPADao.class);
        bind(PLCUserDao.class).to(PLCUserJPADao.class);
        bind(ConsumerDao.class).to(ConsumerJPADao.class);
        bind(TokenDao.class).to(TokenJPADao.class);
        bind(ResearchEntityDao.class).to(ResearchEntityJPADao.class);
        bind(PersistentServiceInitializer.class).asEagerSingleton();
    }
}
