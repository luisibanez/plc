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
package com.fiveamsolutions.plc.web.struts2.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ValidationAware;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class HibernateValidatorInterceptorTest {


    /**
     * Test empty exclude methods.
     */
    @Test
    public void testEmptyExcludeMethods() {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        testInterceptor.setExcludeMethods("  ");
        assertEquals("", testInterceptor.getExcludeMethods());
    }

    /**
     * Test non empty exclude methods.
     */
    @Test
    public void testNonEmptyExcludeMethods() {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        testInterceptor.setExcludeMethods(" m1,   m2 , m3 , , m4 ");
        assertEquals("m1,m2,m3,m4", testInterceptor.getExcludeMethods());
    }

    /**
     * Test skip validation for not validation aware object.
     * @throws Exception if the intercept call fails.
     */
    @Test
    public void testSkipValidationForNotValidationAwareAction() throws Exception {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        ActionInvocation testInvocation = mock(ActionInvocation.class);
        ActionProxy testProxy = mock(ActionProxy.class);
        Object testObject = new Object();
        when(testInvocation.getAction()).thenReturn(testObject);
        when(testInvocation.getProxy()).thenReturn(testProxy);
        testInterceptor.intercept(testInvocation);
        verify(testInvocation).invoke();
    }

    /**
     * Test skip validation for excluded method.
     * @throws Exception if the intercept call fails.
     */
    @Test
    public void testSkipValidationForExcludedMethod() throws Exception {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        String testMethodName = "excludedMethod";
        testInterceptor.setExcludeMethods(testMethodName);
        verifySkipValidationForMethod(testInterceptor, testMethodName);
    }

    /**
     * Test skip validation for annotated method.
     * @throws Exception if the intercept call fails.
     */
    @Test
    public void testSkipValidationForAnnotatedMethod() throws Exception {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        verifySkipValidationForMethod(testInterceptor, "skippedMethod");
    }

    /**
     * Test validation when there are no violated constraints.
     * @throws Exception if the intercept call fails.
     */
    @Test
    public void testNoConstraintsViolated() throws Exception {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        ValidationAwareAndTextProviderTestAction testAction = new ValidationAwareAndTextProviderTestAction();
        testAction.setField("not null");
        ActionInvocation testInvocation = getTestInvocationForMethod("validatedMethod", testAction);
        testInterceptor.intercept(testInvocation);
        verify(testInvocation).invoke();
    }

    /**
     * Test validation when there are violated constraints and the action implements a text provider.
     * @throws Exception if the intercept call fails.
     */
    @Test
    public void testViolatedConstraintsForTextProviderAction() throws Exception {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        ValidationAwareAndTextProviderTestAction testAction = new ValidationAwareAndTextProviderTestAction();
        ActionInvocation testInvocation = getTestInvocationForMethod("validatedMethod", testAction);
        assertEquals("input", testInterceptor.intercept(testInvocation));
        assertTrue(testAction.hasErrors());
    }

    /**
     * Test validation when there are violated constraints when the text provider is not available.
     * @throws Exception if the intercept call fails.
     */
    @Test
    public void testViolatedConstraintsWithoutTextProvider() throws Exception {
        HibernateValidatorInterceptor testInterceptor = new HibernateValidatorInterceptor();
        ValidationAwareOnlyTestAction testAction = new ValidationAwareOnlyTestAction();
        ActionInvocation testInvocation = getTestInvocationForMethod("validatedMethod", testAction);
        assertEquals("input", testInterceptor.intercept(testInvocation));
        assertTrue(testAction.hasErrors());
    }

    private void verifySkipValidationForMethod(HibernateValidatorInterceptor testInterceptor, String testMethodName)
            throws Exception {
        ActionInvocation testInvocation = getTestInvocationForMethod(testMethodName,
                new ValidationAwareAndTextProviderTestAction());
        testInterceptor.intercept(testInvocation);
        verify(testInvocation).invoke();
    }

    private ActionInvocation getTestInvocationForMethod(String testMethodName, ValidationAware testAction) {
        ActionInvocation testInvocation = mock(ActionInvocation.class);
        ActionProxy testProxy = mock(ActionProxy.class);
        when(testInvocation.getAction()).thenReturn(testAction);
        when(testInvocation.getProxy()).thenReturn(testProxy);
        when(testProxy.getMethod()).thenReturn(testMethodName);
        return testInvocation;
    }

}
