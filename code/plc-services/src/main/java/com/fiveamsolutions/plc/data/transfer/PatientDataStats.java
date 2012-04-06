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
package com.fiveamsolutions.plc.data.transfer;

import java.io.Serializable;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class PatientDataStats implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long fileCount;
    private final Long guidCount;
    private final Long fileDataSize;

    /**
     * Class constructor.
     *
     * @param fileCount the file count
     * @param guidCount the guid count
     * @param fileDataSize the sum of file data size
     */
    public PatientDataStats(Long fileCount, Long guidCount, Long fileDataSize) {
        this.fileCount = fileCount;
        this.guidCount = guidCount;
        this.fileDataSize = fileDataSize;
    }

    /**
     * @return the fileCount
     */
    public long getFileCount() {
        return fileCount.longValue();
    }

    /**
     * @return the guidCount
     */
    public long getGuidCount() {
        return guidCount.longValue();
    }

    /**
     * @return the fileDataSize
     */
    public long getFileDataSize() {
        return fileDataSize == null ? 0 : fileDataSize.longValue();
    }
}
