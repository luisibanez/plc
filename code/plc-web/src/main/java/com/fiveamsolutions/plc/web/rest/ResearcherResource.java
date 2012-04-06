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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.fiveamsolutions.plc.dao.ResearchEntityDao;
import com.fiveamsolutions.plc.dao.oauth.TokenDao;
import com.fiveamsolutions.plc.data.ResearchEntity;
import com.fiveamsolutions.plc.data.oauth.OAuthToken;
import com.fiveamsolutions.plc.data.transfer.DownloadDetails;
import com.fiveamsolutions.plc.data.transfer.Filter;
import com.fiveamsolutions.plc.data.transfer.Summary;
import com.fiveamsolutions.plc.service.PatientDataService;
import com.fiveamsolutions.plc.util.PLCApplicationResources;
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
    private static final String FILE_LOCATION_KEY = "file.storage.location";
    private final ResearchEntityDao researchEntityDao;
    private final TokenDao tokenDao;
    private final PatientDataService patientDataService;
    private final PLCApplicationResources appResources;
    @Context
    private HttpContext httpContext;

    /**
     * Class constructor.
     * @param researchEntityDao the research entity dao
     * @param tokenDao the token dao
     * @param patientDataService the patient data service
     * @param appResources the application resources
     */
    @Inject
    public ResearcherResource(ResearchEntityDao researchEntityDao, TokenDao tokenDao,
            PatientDataService patientDataService, PLCApplicationResources appResources) {
        this.researchEntityDao = researchEntityDao;
        this.tokenDao = tokenDao;
        this.patientDataService = patientDataService;
        this.appResources = appResources;
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
     * Retrieves patient shared data summary.
     * @param filter the filter
     * @return the data summary
     */
    @RolesAllowed({OAuthToken.RESEARCHER_ROLE })
    @POST
    @Path("shared/summary")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Summary dataSummary(Filter filter) {
        return patientDataService.getSummary(filter);
    }

    /**
     * Retrieves patient shared data summary.
     * @param filter the filter
     * @return the data summary
     */
    @RolesAllowed({OAuthToken.RESEARCHER_ROLE })
    @POST
    @Path("shared")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DownloadDetails requestData(Filter filter) {
        return patientDataService.getDownloadDetails(filter);
    }

    /**
     * Downloads the compiled patient data.
     * @param fileName the file name to download
     * @return the file to download
     */
    @Path("shared/download/{fileName}")
    @RolesAllowed({OAuthToken.RESEARCHER_ROLE })
    @GET
    public Response downloadPatientData(@PathParam("fileName") final String fileName) {
        final String filePath = appResources.getStringResource(FILE_LOCATION_KEY);
        StreamingOutput output = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException {
                try {
                    InputStream input = FileUtils.openInputStream(FileUtils.getFile(filePath, fileName + ".zip"));
                    IOUtils.copyLarge(input, output);
                } catch (Exception e) {
                    throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
                }
            }
        };
        return Response.ok(output, "application/zip")
                .header("content-disposition", "attachment; filename = patientData.zip").build();
    }

    /**
     * @param httpContext the http context to set
     */
    void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }
}
