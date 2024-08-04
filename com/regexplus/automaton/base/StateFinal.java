package com.regexplus.automaton.base;

import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;

public class StateFinal extends State {
    public StateFinal() {
        super();
    }

    public StateType getType() {
        return StateType.FINAL;
    }
}