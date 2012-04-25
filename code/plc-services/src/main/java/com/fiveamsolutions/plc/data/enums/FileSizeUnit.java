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
package com.fiveamsolutions.plc.data.enums;

import org.apache.commons.io.FileUtils;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public enum FileSizeUnit {
    /** Bytes.*/
    B("B"),
    /** Kilobytes.*/
    KB("KB"),
    /** Megabytes.*/
    MB("MB"),
    /** Gigabytes.*/
    GB("GB"),
    /** Terrabytes.*/
    TB("TB");

    private String code;

    /**
     * Class constructor.
     * @param code
     */
    private FileSizeUnit(String code) {
        this.code = code;
    }

    /**
     * @return the country
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name();
    }

    /**
     * Returns the appropriate file size unit for a given number of bytes.
     * @param size the number of bytes
     * @return the file size unit
     */
    public static FileSizeUnit getUnit(long size) {
        FileSizeUnit unit;
        if (size / FileUtils.ONE_TB > 0) {
            unit  = TB;
        } else if (size / FileUtils.ONE_GB > 0) {
            unit = GB;
        } else if (size / FileUtils.ONE_MB > 0) {
            unit = MB;
        } else if (size / FileUtils.ONE_KB > 0) {
            unit = KB;
        } else {
            unit = B;
        }
        return unit;
    }
}
