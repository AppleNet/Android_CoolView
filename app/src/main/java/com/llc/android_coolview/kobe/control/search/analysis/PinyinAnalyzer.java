package com.llc.android_coolview.kobe.control.search.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

/**
 * @author liulongchao
 */
final public class PinyinAnalyzer extends Analyzer {

    protected final Version matchVersion;
    protected final boolean convertToT9;

    public PinyinAnalyzer(Version version, boolean convertToT9) {
        matchVersion = version;
        this.convertToT9 = convertToT9;
    }

    @Override
    protected TokenStreamComponents createComponents(String s, Reader reader) {
        Tokenizer source = new LetterTokenizer(matchVersion, reader);
        TokenStream filter =  new LowerCaseFilter(matchVersion, source);
        if (convertToT9) {
            filter = new T9Filter(matchVersion, filter);
        }
        filter = new EdgeNGramTokenFilter(filter, EdgeNGramTokenFilter.Side.FRONT, 1, 10);
        return new TokenStreamComponents(source, filter);
    }

    /**
     * Override this if you want to add a CharFilter chain.
     * <p/>
     * The default implementation returns <code>reader</code>
     * unchanged.
     *
     * @param fieldName IndexableField name being indexed
     * @param reader    original Reader
     * @return reader, optionally decorated with CharFilter(s)
     */
    protected Reader initReader(String fieldName, Reader reader) {
        return reader;
    }
}