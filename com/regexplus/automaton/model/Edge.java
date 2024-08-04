package com.regexplus.automaton.model;

import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;

public class Edge implements IEdge {
    IState start, finish;

    public Edge(IState start, IState finish) {
        this.start = start;
        this.finish = finish;
        this.start.getOutputEdges().add(this);
        this.finish.getInputEdges().add(this);
    }

    public EdgeType getType() {
        return EdgeType.EMPTY;
    }

    public IState getStart() {
        return this.start;
    }

    public IState getFinish() {
        return this.finish;
    }

    public boolean accepts(char letter) {
        return false;
    }
}