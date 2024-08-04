package com.regexplus.automaton.model;

import com.regexplus.automaton.common.IStream;

public class StringStream implements IStream {
    String source;
    int currentPosition;

    public StringStream(String source) {
        this.source = source;
        this.currentPosition = 0;
    }

    public boolean atEnd() {
        return this.currentPosition >= this.source.length();
    }

    public int next() {
        this.currentPosition++;
        return this.atEnd() ? -1 : this.current();
    }

    public int current() {
        return this.atEnd() ? -1 : this.source.charAt(this.currentPosition);
    }

    public int position() {
        return this.currentPosition;
    }

    public String getSource() {
        return this.source;
    }
}
