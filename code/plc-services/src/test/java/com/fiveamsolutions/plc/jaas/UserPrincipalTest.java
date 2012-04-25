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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.fiveamsolutions.plc.data.PLCUser;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class UserPrincipalTest {
    private static final Long TEST_ID = 1L;
    private static final String TEST_USERNAME = "test";

    /**
     * Test invalid constructor with null argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructorWithNullArgument() {
        new UserPrincipal(null);
        fail("IllegalArgumentException expected");
    }

    /**
     * Test invalid constructor with null ID.
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructorWithNullUsername() {
        createTestUserPrincipal(null, TEST_USERNAME);
        fail("IllegalArgumentException expected");
    }

    /**
     * Test valid constructor.
     */
    @Test
    public void validConstructor() {
        UserPrincipal testPrincipal = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        assertNotNull(testPrincipal);
    }

    /**
     * Test hash code.
     */
    @Test
    public void testHashCode() {
        UserPrincipal testUserPrincipal1 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        UserPrincipal testUserPrincipal2 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        assertEquals(testUserPrincipal1.hashCode(), testUserPrincipal2.hashCode());
    }

    /**
     * Test equality when the two objects are the same.
     */
    @Test
    public void equalityForSameObject() {
        UserPrincipal testUserPrincipal = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        assertTrue(testUserPrincipal.equals(testUserPrincipal));
    }

    /**
     * Test equality of two different objects with the same ID and name.
     */
    @Test
    public void equalsWhenIDsAndNamesAreEqual() {
        UserPrincipal testUserPrincipal1 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        UserPrincipal testUserPrincipal2 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        assertTrue(testUserPrincipal1.equals(testUserPrincipal2));
    }

    /**
     * Test equality of two different objects with the same ID and different names.
     */
    @Test
    public void equalsWithSameIDsAndDifferentNames() {
        UserPrincipal testUserPrincipal1 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        UserPrincipal testUserPrincipal2 = createTestUserPrincipal(TEST_ID, "test1");
        assertFalse(testUserPrincipal1.equals(testUserPrincipal2));
    }

    /**
     * Test inequality with null.
     */
    @Test
    public void inequalityWithNull() {
        UserPrincipal testUserPrincipal = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        assertFalse(testUserPrincipal.equals(null));
    }

    /**
     * Test inequality with null name.
     */
    @Test
    public void inequalityWithNullName() {
        UserPrincipal testUserPrincipal1 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        UserPrincipal testUserPrincipal2 = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        testUserPrincipal1.getUser().setUsername(null);
        assertFalse(testUserPrincipal1.equals(testUserPrincipal2));
    }

    /**
     * Test inequality with object of a different class.
     */
    @Test
    public void inequalityWhenNotSameClass() {
        UserPrincipal testUserPrincipal = createTestUserPrincipal(TEST_ID, TEST_USERNAME);
        assertFalse(testUserPrincipal.equals(new Object()));
    }

    private UserPrincipal createTestUserPrincipal(Long id, String username) {
        PLCUser testUser = new PLCUser();
        testUser.setId(id);
        testUser.setUsername(username);
        return new UserPrincipal(testUser);
    }
}
