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
package com.fiveamsolutions.plc.web.rest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.PatientDataDao;
import com.fiveamsolutions.plc.dao.ResearchEntityDao;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.dao.oauth.TokenDao;
import com.fiveamsolutions.plc.data.ResearchEntity;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.core.HttpRequestContext;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class ResearcherResourceTest {
    private ResearcherResource researcherResource;
    private ResearchEntityDao researchEntityDao;
    private TokenDao tokenDao;
    private PatientDataDao patientDataDao;
    private HttpContext context;

    /**
     * Setup test data.
     */
    @Before
    public void setUp() {
        researchEntityDao = mock(ResearchEntityDao.class);
        tokenDao = mock(TokenDao.class);
        patientDataDao = mock(PatientDataDao.class);
        context = mock(HttpContext.class);
        HttpRequestContext request = mock(HttpRequestContext.class);

        when(context.getRequest()).thenReturn(request);
        when(tokenDao.getByToken(anyString())).thenReturn(TestPLCEntityFactory.createToken());
        when(patientDataDao.getPatientDataSummary(any(Filter.class))).thenReturn(TestPLCEntityFactory.createSummary());
        researcherResource = new ResearcherResource(researchEntityDao, tokenDao, patientDataDao);
    }

    /**
     * Tests qualification request.
     */
    @Test
    public void qualificationRequest() {
        ResearchEntity re = TestPLCEntityFactory.createResearchEntity();
        researcherResource.setHttpContext(context);
        String results = researcherResource.qualificationRequest(re);
        assertNotNull(results);
    }

    /**
     * Tests data summary retrieval.
     */
    @Test
    public void dataSummary() {
        Summary summary = researcherResource.dataSummary(new Filter());
        assertNotNull(summary);
    }

}
