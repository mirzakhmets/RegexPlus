package com.regexplus.automaton.base;

import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.Edge;

public class EdgeEmpty extends Edge {
    public EdgeEmpty(IState start, IState finish) {
        super(start, finish);
    }

    @Override
    public EdgeType getType() {
        return EdgeType.EMPTY;
    }
}