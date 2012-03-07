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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.fiveamsolutions.plc.data.PLCEntity;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public abstract class AbstractPLCJPADaoTest<T extends PLCEntity> extends AbstractJPADaoTest {
    private static final int NUMBER_OF_TEST_ENTITIES = 10;

    /**
     * Tests retrieve all.
     */
    @Test
    public void getAll() {
        persistMultipleTestEntities(NUMBER_OF_TEST_ENTITIES);
        AbstractPLCEntityDao<T> testDao = getTestDao();
        testDao.getEntityManager().getTransaction().begin();
        List<T> results = testDao.getAll();
        testDao.getEntityManager().getTransaction().commit();
        assertFalse(results.isEmpty());
        assertEquals(NUMBER_OF_TEST_ENTITIES, results.size());
    }

    /**
     * Test delete functionality.
     */
    @Test
    public void delete() {
        T entity = persistTestEntity();
        assertNotNull(retrieveById(entity.getId()));
        AbstractPLCEntityDao<T> testDao = getTestDao();
        testDao.getEntityManager().getTransaction().begin();
        testDao.deleteById(entity.getId());
        testDao.getEntityManager().getTransaction().commit();
        assertNull(retrieveById(entity.getId()));
    }

    /**
     * Tests deletion of an invalid entity.
     */
    @Test
    public void invalidDeletion() {
        AbstractPLCEntityDao<T> testDao = getTestDao();
        testDao.getEntityManager().getTransaction().begin();
        testDao.deleteById(0L);
        testDao.getEntityManager().getTransaction().commit();
    }

    /**
     * Tests update functionality.
     */
    @Test
    public void update() {
        T testEntity = persistTestEntity();
        changeTestEntity(testEntity);
        testEntity  = persistTestEntity(testEntity);
        PLCEntity retrievedEntity = retrieveById(testEntity.getId());
        assertNotNull(retrievedEntity);
        assertNotSame(testEntity, retrievedEntity);
        assertEquals(testEntity.getId(), retrievedEntity.getId());
    }

    /**
     * Test the parameterized type.
     */
    @Test
    public void testGetParameterizedType() {
        AbstractPLCEntityDao<T> testDao = getTestDao();
        PLCEntity testEntity = getTestEntity();
        assertNotNull(testDao.getEntityType());
        assertTrue(testDao.getEntityType().isAssignableFrom(testEntity.getClass()));
    }

    /**
     * Get the DAO to be tests.
     * @return the DAO to be tested
     */
    protected abstract AbstractPLCEntityDao<T> getTestDao();

    /**
     * Get an entity to be used for testing.
     * @return a test entity
     */
    protected abstract T getTestEntity();

    /**
     * Change any attribute(s) of the given test entity.
     * @param testEntity the test entity to be modified.
     */
    protected abstract void changeTestEntity(PLCEntity testEntity);

    /**
     * Persist multiple test entities.
     * @param n the number of entities to persist.
     */
    protected void persistMultipleTestEntities(int n) {
        for (int i = 0; i < n; i++) {
            persistTestEntity();
        }
    }

    /**
     * Retrieve an entity by ID for verification purposes.
     * @param id entity's ID.
     * @return the retrieved entity.
     */
    protected T retrieveById(Long id) {
        AbstractPLCEntityDao<T> testDao = getTestDao();
        T retrievedEntity;
        testDao.getEntityManager().getTransaction().begin();
        retrievedEntity = testDao.getById(id);
        testDao.getEntityManager().getTransaction().commit();
        return retrievedEntity;
    }

    /**
     * Create and persist a test entity.
     * @return the persisted entity.
     */
    protected T persistTestEntity() {
        T testEntity = getTestEntity();
        return persistTestEntity(testEntity);
    }

    /**
     * Persist the given entity.
     * @param testEntity entity to be persisted.
     * @return the persisted entity.
     */
    protected T persistTestEntity(T testEntity) {
        AbstractPLCEntityDao<T> testDao = getTestDao();
        try {
            testDao.getEntityManager().getTransaction().begin();
            testDao.save(testEntity);
            testDao.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            testDao.getEntityManager().getTransaction().rollback();
            fail(e.getMessage());
        }
        testDao.getEntityManager().clear();
        return testEntity;
    }
}
