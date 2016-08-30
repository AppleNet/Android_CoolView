package com.llc.android_coolview.kobe.control.search.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;

/**
 * @author liulongchao
 */
public class T9Analyzer extends Analyzer {
    protected final Version matchVersion;
    public T9Analyzer(Version version)
    {
        matchVersion = version;
    }

    @Override
    protected TokenStreamComponents createComponents(String s, Reader reader) {
        Tokenizer source = new WhitespaceTokenizer(matchVersion,reader);
        TokenStream filter = new T9Filter(matchVersion,new LowerCaseFilter(matchVersion,source));
        return new TokenStreamComponents(source, filter);
    }
}