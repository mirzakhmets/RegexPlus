package com.regexplus.automaton.common;

public interface IStream {
    boolean atEnd();

    int next();

    int current();

    int position();
}
