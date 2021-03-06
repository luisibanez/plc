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
package com.fiveamsolutions.plc.dao;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.fiveamsolutions.plc.data.ChallengeQuestion;
import com.fiveamsolutions.plc.data.PLCUser;
import com.fiveamsolutions.plc.data.PatientAccount;
import com.fiveamsolutions.plc.data.PatientData;
import com.fiveamsolutions.plc.data.PatientDemographics;
import com.fiveamsolutions.plc.data.ResearchEntity;
import com.fiveamsolutions.plc.data.enums.Country;
import com.fiveamsolutions.plc.data.enums.FileSizeUnit;
import com.fiveamsolutions.plc.data.enums.PatientDataSource;
import com.fiveamsolutions.plc.data.enums.PatientDataType;
import com.fiveamsolutions.plc.data.oauth.Consumer;
import com.fiveamsolutions.plc.data.oauth.OAuthToken;
import com.fiveamsolutions.plc.data.transfer.DownloadDetails;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Patient;
import com.fiveamsolutions.plc.data.transfer.Summary;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.core.util.UnmodifiableMultivaluedMap;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class TestPLCEntityFactory {
    private static final int PASSWORD_LENGTH = 20;
    private static final int USERNAME_LENGTH = 20;
    private static final int GUID_LENGTH = 64;

    /**
     * Creates a patient demographics entity for testing.
     * @return the patient demographics entity
     */
    public static PatientDemographics createPatientDemographics() {
        PatientDemographics pd = new PatientDemographics();
        pd.setFirstName("firstName");
        pd.setBirthName("birthName");
        pd.setBirthCountry(Country.US);
        pd.setBirthPlace("birthPlace");
        pd.setBirthDate(DateUtils.addYears(new Date(), -1));
        return pd;
    }

    /**
     * Creates a patient account entity for testing.
     * @return the patient account entity
     */
    public static PatientAccount createPatientAccount() {
        PatientAccount pa = new PatientAccount();
        pa.setGuid(RandomStringUtils.randomAlphanumeric(GUID_LENGTH));
        pa.setPatientDemographics(createPatientDemographics());
        pa.setPlcUser(createPLCUser());

        ChallengeQuestion challenge = new ChallengeQuestion();
        challenge.setQuestion("Mother's Maiden Name");
        challenge.setAnswer("Foo");

        List<ChallengeQuestion> questions = new ArrayList<ChallengeQuestion>();
        questions.add(challenge);
        pa.setChallengeQuestions(questions);
        return pa;
    }

    public static PLCUser createPLCUser() {
        PLCUser user = new PLCUser();
        user.setFullName("Full Name");
        user.setEmail("test@example.com");
        user.setPassword(RandomStringUtils.randomAscii(PASSWORD_LENGTH));
        user.setUsername(RandomStringUtils.randomAlphanumeric(USERNAME_LENGTH));
        return user;
    }
    /**
     * Creates patient transfer entity for testing.
     * @return the patient
     */
    public static Patient createPatient() {
        Patient patient = new Patient();
        patient.setEmail("test@example.com");
        patient.setPassword(RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH));
        patient.setUsername(RandomStringUtils.randomAlphanumeric(USERNAME_LENGTH));
        patient.setFirstName("firstName");
        patient.setBirthName("birthName");
        patient.setBirthCountry(Country.US);
        patient.setBirthPlace("birthPlace");
        patient.setBirthDate(new Date());

        ChallengeQuestion challenge = new ChallengeQuestion();
        challenge.setQuestion("Mother's Maiden Name");
        challenge.setAnswer("Foo");

        List<ChallengeQuestion> questions = new ArrayList<ChallengeQuestion>();
        questions.add(challenge);
        patient.setChallengeQuestions(questions);
        return patient;
    }

    /**
     * Creates a patient data entity for testing.
     * @return the patient data
     */
    public static PatientData createPatientData() {
        PatientData patientData = new PatientData();
        patientData.setDataType(PatientDataType.SNP_GENOTYPE);
        patientData.setDataSource(PatientDataSource.TWENTY_THREE_AND_ME);
        patientData.setVersion("v1");
        patientData.setNotes("This is a test file.");
        patientData.setFileName(UUID.randomUUID().toString() + ".txt");

        try {
            InputStream inputData = TestPLCEntityFactory.class.getClassLoader().getResourceAsStream("patient_data.txt");
            patientData.setFileData(IOUtils.toByteArray(inputData));
        } catch (IOException e) {
            fail("Error loading test data.");
        }
        patientData.setTags(Arrays.asList("AMD", "Experimental"));
        patientData.setPatientAccount(createPatientAccount());
        return patientData;
    }

    /**
     * Creates a research entity.
     * @return the research entity
     */
    public static ResearchEntity createResearchEntity() {
        ResearchEntity re = new ResearchEntity();
        re.setName("Research Entity");
        re.setEmail("re@xample.com");
        re.setTelephone("1-123-456-7890");
        re.setUrl("http://www.example.com");
        re.setDescription("Test Research entity for unit tests.");
        re.setToken(createToken());
        return re;
    }

    /**
     * Creates a consumer.
     * @return the consumer
     */
    public static Consumer createConsumer() {
        Consumer consumer = new Consumer();
        consumer.setKey(RandomStringUtils.randomAlphanumeric(25));
        consumer.setSecret(RandomStringUtils.randomAlphanumeric(25));
        return consumer;
    }

    /**
     * Creates a token.
     * @return the token
     */
    public static OAuthToken createToken() {
        OAuthToken token = new OAuthToken();
        token.setToken(RandomStringUtils.randomAlphanumeric(25));
        token.setSecret(RandomStringUtils.randomAlphanumeric(25));
        token.setAttributes(new UnmodifiableMultivaluedMap<String, String>(new MultivaluedMapImpl()));
        token.setConsumer(createConsumer());
        return token;
    }

    /**
     * Creates a filter.
     * @return the filter
     */
    public static Filter createFilter() {
        Filter filter = new Filter();
        Set<String> pgguids = new HashSet<String>();
        pgguids.add(RandomStringUtils.randomAlphanumeric(GUID_LENGTH));
        pgguids.add(RandomStringUtils.randomAlphanumeric(GUID_LENGTH));
        pgguids.add(RandomStringUtils.randomAlphanumeric(GUID_LENGTH));
        filter.setPguids(pgguids);

        Set<String> tags = new HashSet<String>();
        tags.add("sample");
        tags.add("curated");
        filter.setTags(tags);
        filter.setLastChangeDate(new Date());
        return filter;
    }

    /**
     * Creates a summary.
     * @return the filter
     */
    public static Summary createSummary() {
        Summary summary = new Summary();
        summary.setTotalFileCount(100);
        summary.setFilteredFileCount(50);
        summary.setTotalPGUIDCount(100);
        summary.setFilteredPGUIDCount(50);
        summary.setTotalFileSize(10);
        summary.setTotalFileSizeUnit(FileSizeUnit.GB);
        summary.setFilteredFileSize(500);
        summary.setFilteredFileSizeUnit(FileSizeUnit.MB);

        Map<String, Integer> countPerTag = new HashMap<String, Integer>();
        countPerTag.put("sample", 50);
        countPerTag.put("curated", 50);

        summary.setFileCountPerTag(countPerTag);

        Map<String, Integer> filteredCountPerTag = new HashMap<String, Integer>();
        filteredCountPerTag.put("sample", 25);
        filteredCountPerTag.put("curated", 25);
        summary.setFilteredFileCountPerTag(filteredCountPerTag);
        return summary;
    }

    /**
     * Creates a new instance of download details.
     * @return the newly created download details
     */
    public static DownloadDetails createDownloadDetails() {
        DownloadDetails details = new DownloadDetails();
        details.setSize(100);
        details.setExpirationDate(DateUtils.addHours(new Date(), 10));
        details.setUnit(FileSizeUnit.MB);
        details.setUrl("http://localhost/rest/download/fileName");
        return details;
    }
}
