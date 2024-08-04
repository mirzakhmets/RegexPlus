package com.regexplus.automaton.dfa;

public class InputEdge {
    public DeterministicState state;
    public int symbol;

    public InputEdge(DeterministicState state, int symbol) {
        this.state = state;
        this.symbol = symbol;
    }
}
