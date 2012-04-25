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
package com.fiveamsolutions.plc.data.oauth;

import java.security.Principal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fiveamsolutions.plc.data.PLCEntity;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.server.spi.OAuthConsumer;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@Entity(name = "oauth_token")
public class OAuthToken implements com.sun.jersey.oauth.server.spi.OAuthToken, PLCEntity {
    private static final long serialVersionUID = 1L;
    /** Regular user role.*/
    public static final String PLC_ROLE = "plcuser";
    /** Researcher role.*/
    public static final String RESEARCHER_ROLE = "researcher";

    private Long id;
    private String token;
    private String secret;
    private boolean authorized = false;
    private boolean researcher = false;
    private MultivaluedMap<String, String> attributes = new MultivaluedMapImpl();
    private OAuthConsumer consumer;

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "token", unique = true, nullable = false)
    @Type(type = "text")
    @Override
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * {@inheritDoc}
     */
    @Column(name = "token_secret", unique = true, nullable = false)
    @Type(type = "text")
    @Override
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the authorized
     */
    @Column(name = "authorized" , nullable = false)
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * @param authorized the authorized to set
     */
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    /**
     * @return the researcher
     */
    public boolean isResearcher() {
        return researcher;
    }

    /**
     * @param researcher the researcher to set
     */
    public void setResearcher(boolean researcher) {
        this.researcher = researcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ManyToOne(targetEntity = com.fiveamsolutions.plc.data.oauth.Consumer.class, cascade = CascadeType.ALL,
        optional = false)
    @JoinColumn(name = "consumer_id")
    public OAuthConsumer getConsumer() {
        return consumer;
    }

    /**
     * @param consumer the consumer to set
     */
    public void setConsumer(OAuthConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public MultivaluedMap<String, String> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(MultivaluedMap<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public Principal getPrincipal() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public boolean isInRole(String role) {
        boolean inRole = false;
        if (this.isAuthorized()) {
            if (StringUtils.equals(RESEARCHER_ROLE, role) && this.isResearcher()) {
                inRole = true;
            } else if (StringUtils.equalsIgnoreCase(PLC_ROLE, role)) {
                inRole = true;
            }
        }
        return inRole;
    }
}
