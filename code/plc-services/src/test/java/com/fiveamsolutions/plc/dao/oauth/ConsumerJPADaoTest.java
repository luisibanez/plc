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
package com.fiveamsolutions.plc.dao.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.plc.dao.AbstractPLCJPADaoTest;
import com.fiveamsolutions.plc.dao.TestPLCEntityFactory;
import com.fiveamsolutions.plc.data.PLCEntity;
import com.fiveamsolutions.plc.data.oauth.Consumer;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class ConsumerJPADaoTest extends AbstractPLCJPADaoTest<Consumer> {
    private ConsumerJPADao testDao;

    /**
     * Prepares test data.
     */
    @Before
    public void prepareTestData() {
        testDao = new ConsumerJPADao(getEntityManager());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ConsumerJPADao getTestDao() {
        return testDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Consumer getTestEntity() {
        return TestPLCEntityFactory.createConsumer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void changeTestEntity(PLCEntity testEntity) {
        Consumer consumer = (Consumer) testEntity;
        consumer.setKey("foo");
    }

    /**
     * Tests retrieving consumer by its key.
     */
    @Test
    public void getByKey() {
        Consumer c = persistTestEntity();
        Consumer retrievedConsumer = getTestDao().getByKey(c.getKey());
        assertNotNull(retrievedConsumer);
        assertEquals(c.getKey(), retrievedConsumer.getKey());
        assertEquals(c.getSecret(), retrievedConsumer.getSecret());
        assertEquals(c.getPrincipal(), retrievedConsumer.getPrincipal());
        assertEquals(c.isInRole("plcuser"), c.isInRole("plcuser"));

        retrievedConsumer = getTestDao().getByKey("wrongKey");
        assertNull(retrievedConsumer);
    }
}
