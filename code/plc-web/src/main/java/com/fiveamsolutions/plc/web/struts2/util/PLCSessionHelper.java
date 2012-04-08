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
package com.fiveamsolutions.plc.web.struts2.util;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.fiveamsolutions.plc.data.PatientDemographics;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PLCSessionHelper {
    private static final String PATIENT_DEMOGRAPHICS = "patientDemographics";
    private static final String GUID = "guid";
    private static final String LOGGED_IN = "loggedIn";

    /**
     * @param demographics the demographics
     * @param session the session
     */
    public static void setPatientDemographics(PatientDemographics demographics, Map<String, Object> session) {
        session.put(PATIENT_DEMOGRAPHICS, demographics);
    }

    /**
     * @param session the session
     * @return the stored patient demographics
     */
    public static PatientDemographics getPatientDemographics(Map<String, Object> session) {
        return (PatientDemographics) session.get(PATIENT_DEMOGRAPHICS);
    }

    /**
     * @param guid the guid
     * @param session the session
     */
    public static void setPatientGUID(String guid, Map<String, Object> session) {
        session.put(GUID, guid);
    }

    /**
     * @param session the session
     * @return the stored guid
     */
    public static String getPatientGUID(Map<String, Object> session) {
        return (String) session.get(GUID);
    }

    /**
     * @param loggedIn whether the user is logged in
     * @param session the session
     */
    public static void setLoggedIn(Boolean loggedIn, HttpSession session) {
        session.setAttribute(LOGGED_IN, loggedIn);
    }

    /**
     * @param session the session
     * @return whether the user is logged in
     */
    public static Boolean getLoggedIn(HttpSession session) {
        return (Boolean) session.getAttribute(LOGGED_IN);
    }
}
