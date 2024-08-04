package com.regexplus.automaton.base;

import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IState;

public class EdgeEmptyNoList extends EdgeEmpty {
    public EdgeEmptyNoList(IState start, IState finish) {
        super(start, finish);
        this.getStart().getOutputEdges().clear();
        this.getFinish().getInputEdges().clear();
    }

    @Override
    public EdgeType getType() {
        return EdgeType.EMPTY;
    }
}