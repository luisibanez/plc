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
package com.fiveamsolutions.plc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.data.PLCEntity;
import com.fiveamsolutions.plc.data.PLCUser;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PLCUserJPADaoTest extends AbstractPLCJPADaoTest<PLCUser> {
    private PLCUserJPADao testDao;

    @Before
    public void prepareTestData() {
        testDao = new PLCUserJPADao(getEntityManager());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PLCUserJPADao getTestDao() {
        return testDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PLCUser getTestEntity() {
        return TestPLCEntityFactory.createPLCUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void changeTestEntity(PLCEntity testEntity) {
        PLCUser user = (PLCUser) testEntity;
        user.setEmail("changed@example.com");
    }

    /**
     * Tests retrieval by username.
     */
    @Test
    public void getByUsername() {
        PLCUser user = getTestEntity();
        getTestDao().getEntityManager().getTransaction().begin();
        getTestDao().save(user);

        PLCUser retrievedUser = getTestDao().getByUsername(user.getUsername());
        assertNotNull(retrievedUser);
        getTestDao().getEntityManager().getTransaction().commit();
        assertEquals(user.getUsername(), retrievedUser.getUsername());
    }
}
