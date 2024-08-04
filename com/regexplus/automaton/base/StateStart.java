package com.regexplus.automaton.base;

import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;

public class StateStart extends State {
    public StateStart() {
        super();
    }

    public StateType getType() {
        return StateType.START;
    }
}
