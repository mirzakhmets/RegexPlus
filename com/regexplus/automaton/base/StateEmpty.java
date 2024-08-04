package com.regexplus.automaton.base;

import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;

public class StateEmpty extends State {
    public StateEmpty() {
        super();
    }

    public StateType getType() {
        return StateType.EMPTY;
    }
}
