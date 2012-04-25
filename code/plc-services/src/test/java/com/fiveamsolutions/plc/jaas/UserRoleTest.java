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
package com.fiveamsolutions.plc.jaas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class UserRoleTest {

    /**
     * Test invalid constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructor() {
        new UserRole(null);
    }

    /**
     * Test hash code.
     */
    @Test
    public void testHashCode() {
        String testRoleName = "testRole";
        UserRole testRole1 = new UserRole(testRoleName);
        UserRole testRole2 = new UserRole(testRoleName);
        assertEquals(testRole1.hashCode(), testRole2.hashCode());
    }

    /**
     * Test equality when the two objects are the same.
     */
    @Test
    public void equalityForSameObject() {
        UserRole testRole = new UserRole("testRole");
        assertTrue(testRole.equals(testRole));
    }

    /**
     * Test equality of two different objects with the same name.
     */
    @Test
    public void equalsWhenNamesAreEqual() {
        String testRoleName = "testRole";
        UserRole testRole1 = new UserRole(testRoleName);
        UserRole testRole2 = new UserRole(testRoleName);
        assertTrue(testRole1.equals(testRole2));
    }

    /**
     * Test inequality with null.
     */
    @Test
    public void inequalityWithNull() {
        UserRole testRole = new UserRole("testRole");
        assertFalse(testRole.equals(null));
    }

    /**
     * Test inequality with object of a different class.
     */
    @Test
    public void inequalityWhenNotSameClass() {
        UserRole testRole = new UserRole("testRole");
        assertFalse(testRole.equals(new Object()));
    }

}
