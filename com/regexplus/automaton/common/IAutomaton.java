package com.regexplus.automaton.common;

import com.regexplus.automaton.model.StringStream;
import com.regexplus.match.common.IMatch;

import java.util.List;

public interface IAutomaton {
    IState getStart();

    IState getFinish();

    void build(StringStream stream);

    List<IMatch> match(IStream stream);

    boolean matches(IStream stream);
}
