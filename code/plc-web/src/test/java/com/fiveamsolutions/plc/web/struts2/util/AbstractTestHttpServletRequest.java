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
package com.fiveamsolutions.plc.web.struts2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public abstract class AbstractTestHttpServletRequest implements HttpServletRequest {

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttribute(String arg0, Object arg1) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAttribute(String arg0) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSecure() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServletContext getServletContext() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getServerPort() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServerName() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getScheme() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRemotePort() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteHost() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteAddr() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRealPath(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProtocol() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getParameterValues(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParameter(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getLocale() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLocalPort() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalName() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalAddr() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentType() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getContentLength() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCharacterEncoding() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAttribute(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() throws ServletException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(String arg0, String arg1) throws ServletException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserInRole(String arg0) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpSession getSession(boolean arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpSession getSession() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServletPath() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRequestedSessionId() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRequestURI() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteUser() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getQueryString() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPathTranslated() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPathInfo() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Part getPart(String arg0) throws IOException, ServletException {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMethod() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIntHeader(String arg0) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getHeaders(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader(String arg0) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDateHeader(String arg0) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cookie[] getCookies() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContextPath() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAuthType() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
        return false;
    }
}
