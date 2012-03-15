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
package com.fiveamsolutions.plc.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fiveamsolutions.plc.data.enums.Country;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
@Entity(name = "patient_data")
public class PatientData implements PLCEntity {
    private static final long serialVersionUID = 1L;

    private static final int NAME_MAX_LENGTH = 50;
    private static final int PLACE_OF_BIRTH_MAX_LENGTH = 100;

    private Long id;
    private String firstName;
    private String birthName;
    private String birthPlace;
    private Country birthCountry;
    private Date birthDate;


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
     * @return the firstName
     */
    @NotEmpty
    @Length(max = NAME_MAX_LENGTH)
    @Column(name = "first_name", nullable = false, updatable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the birthName
     */
    @NotEmpty
    @Length(max = NAME_MAX_LENGTH)
    @Column(name = "birth_name", nullable = false, updatable = false)
    public String getBirthName() {
        return birthName;
    }

    /**
     * @param birthName the birthName to set
     */
    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    /**
     * @return the birthPlace
     */
    @NotEmpty
    @Length(max = PLACE_OF_BIRTH_MAX_LENGTH)
    @Column(name = "birth_place", nullable = false, updatable = false)
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * @return the birthCountry
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "birth_country", nullable = false, updatable = false)
    public Country getBirthCountry() {
        return birthCountry;
    }

    /**
     * @param birthCountry the birthCountry to set
     */
    public void setBirthCountry(Country birthCountry) {
        this.birthCountry = birthCountry;
    }

    /**
     * @return the birthDate
     */
    @Past
    @NotNull
    @Column(name = "birth_date", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
