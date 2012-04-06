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
package com.fiveamsolutions.plc.inject;

import java.util.ArrayList;
import java.util.List;

import com.fiveamsolutions.plc.dao.JPADaoModule;
import com.fiveamsolutions.plc.data.validator.ValidatorModule;
import com.fiveamsolutions.plc.service.PatientDataServiceModule;
import com.fiveamsolutions.plc.service.PatientInformationServiceModule;
import com.fiveamsolutions.plc.service.scheduled.ScheduledJobModule;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
import com.fiveamsolutions.plc.util.PLCResourceBundleProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Common guice module that provides injection for objects used across the entire application.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class GuiceInjectorHolder {

    private static Injector injector;

    /**
     * Get the guice injector instance.
     * @return the injector instance
     */
    public static Injector getInjector() {
        if (injector == null) {
            createInjector();
        }
        return injector;
    }

    /**
     * Create the injector.
     */
    private static synchronized void createInjector() {
        final List<Module> modules = new ArrayList<Module>();
        modules.add(new PLCModule());
        modules.add(new JPADaoModule(new PLCApplicationResources(new PLCResourceBundleProvider().get())));
        modules.add(new PatientInformationServiceModule());
        modules.add(new PatientDataServiceModule());
        modules.add(new ValidatorModule());
        modules.add(new ScheduledJobModule(new PLCApplicationResources(new PLCResourceBundleProvider().get())));
        injector = Guice.createInjector(modules);
    }
}
