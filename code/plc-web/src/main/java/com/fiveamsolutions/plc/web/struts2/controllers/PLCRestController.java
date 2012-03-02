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
package com.fiveamsolutions.plc.web.struts2.controllers;

import org.apache.struts2.rest.HttpHeaders;
import org.apache.struts2.rest.RestActionSupport;

import com.fiveamsolutions.plc.data.PLCEntity;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Default class for implementing REST-ful controllers.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 * @param <T> entity type
 */
public abstract class  PLCRestController<T extends PLCEntity> extends RestActionSupport
    implements ModelDriven<Object>, Preparable {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * Loads the specified entity with the give id.
     * Corresponds to GET /T/{id}.
     * @return the specified entity
     */
    public abstract HttpHeaders show();

    /**
     * Retrieves a list of all entities.
     * Corresponds to GET /T.
     * @return the list of entities associated with this url
     */
    @Override
    public abstract HttpHeaders index();

    /**
     * Requests the entity with the given id for editing.
     * Corresponds to GET /T/{id}/edit.
     * @return the result
     */
    public abstract String edit();

    /**
     * Requests a new object.
     * Corresponds GET /T/new
     * @return the result
     */
    public abstract String editNew();

    /**
     * Deletes the given entity.
     * Corresponds to DELETE /T/{id}.
     * @return the result
     */
    public abstract String destroy();

    /**
     * Creates a new entity, saving it in the data store.
     * Corresponds to POST /T.
     * @return the created entity
     */
    public abstract HttpHeaders create();

    /**
     * Updates the entity with the given id
     * Corresponds to PUT /T/{id}.
     * @return the list of entities associated with this url
     */
    public abstract String update();

    /**
     * Sets the id.
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the id.
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
