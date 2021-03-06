package com.llc.android_coolview.kobe.control.search.analysis;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;


import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;


/**
 * Normalizes token text to lower case.
 * <a name="version"/>
 * <p>You must specify the required {@link org.apache.lucene.util.Version}
 * compatibility when creating LowerCaseFilter:
 * <ul>
 * <li> As of 3.1, supplementary characters are properly lowercased.
 * </ul>
 */
public final class T9Filter extends TokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private static final String T9 = "22233344455566677778889999";


    /**
     * Create a new LowerCaseFilter, that normalizes token text to lower case.
     *
     * @param matchVersion See <a href="#version">above</a>
     * @param in           TokenStream to filter
     */
    public T9Filter(Version matchVersion, TokenStream in) {
        super(in);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (input.incrementToken()) {
            final char[] buffer = termAtt.buffer();
            final int length = termAtt.length();
            for (int i = 0; i < length; ) {
                buffer[i]=convert(buffer[i++]);
            }
            return true;
        } else
            return false;
    }

    private char convert(char c) {
        if (c >= 'a' && c <= 'z') {
            return T9.charAt(c - 'a');
        } else {
            return c;
        }
    }
}
