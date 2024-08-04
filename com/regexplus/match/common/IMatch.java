package com.regexplus.match.common;

import com.regexplus.automaton.common.IStream;
import com.regexplus.match.model.Match;

public interface IMatch {
    IStream stream();

    int start();

    int end();

    int length();

    void setEnd(int end);

    boolean matches();

    Match copy();
}
