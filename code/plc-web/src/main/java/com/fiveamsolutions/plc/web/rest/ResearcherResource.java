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

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fiveamsolutions.plc.dao.ResearchEntityDao;
import com.fiveamsolutions.plc.dao.oauth.TokenDao;
import com.fiveamsolutions.plc.data.ResearchEntity;
import com.fiveamsolutions.plc.data.oauth.OAuthToken;
import com.google.inject.Inject;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.oauth.server.OAuthServerRequest;
import com.sun.jersey.oauth.signature.OAuthParameters;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@Path("/researcher")
@RequestScoped
public class ResearcherResource {
    private final ResearchEntityDao researchEntityDao;
    private final TokenDao tokenDao;
    @Context
    private HttpContext httpContext;

    /**
     * Class constructor.
     * @param researchEntityDao the research entity dao
     * @param tokenDao the token dao
     */
    @Inject
    public ResearcherResource(ResearchEntityDao researchEntityDao, TokenDao tokenDao) {
        this.researchEntityDao = researchEntityDao;
        this.tokenDao = tokenDao;
    }

    /**
     * Research researcher access.
     * @param re the research entity
     * @param
     * @return success message
     */
    @RolesAllowed({OAuthToken.PLC_ROLE })
    @POST
    @Path("qualification_request")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String qualificationRequest(ResearchEntity re) {
        OAuthParameters params = new OAuthParameters();
        params.readRequest(new OAuthServerRequest(httpContext.getRequest()));
        OAuthToken token = tokenDao.getByToken(params.getToken());
        re.setToken(token);
        researchEntityDao.save(re);
        return "Researcher Access Successfully requested.";
    }

    /**
     * @param httpContext the http context to set
     */
    void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }
}
