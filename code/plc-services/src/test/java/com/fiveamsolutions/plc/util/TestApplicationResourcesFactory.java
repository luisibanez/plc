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
package com.fiveamsolutions.plc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 *
 */
public class TestApplicationResourcesFactory {

    /**
     * @return application resources for testing purposes.
     */
    public static PLCApplicationResources getApplicationResources() {
        ResourceBundle testResourceBundle = new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                List<Object[]> contentList = new ArrayList<Object[]>();
                contentList.add(new Object[] {"foo", "bar"});
                contentList.add(new Object[] {"hashing.algorithm", "SHA-256"});
                contentList.add(new Object[] {"hashing.date.format", "yyyyMMdd"});
                contentList.add(new Object[] {"hashing.string.encoding", "UTF-8"});
                contentList.add(new Object[] {"plc.persistenceUnit.name", "plc-testdb"});
                contentList.add(new Object[] {"file.storage.location", "/tmp"});
                contentList.add(new Object[] {"file.storage.duration", "-20"});
                contentList.add(new Object[] {"file.cleanup.interval", "5"});
                contentList.add(new Object[] {"file.storage.duration.invalid", "20a"});
                contentList.add(new Object[] {"file.download.url", ""});

                Object[][] content = new Object[contentList.size()][];
                contentList.toArray(content);
                return content;
            }
        };
        return new PLCApplicationResources(testResourceBundle);
    }

}
