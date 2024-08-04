package com.regexplus.automaton.base;

import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;

public class StateTag extends State {
    public State pairedState = null;

    public StateTag() {
        super();
    }

    public StateType getType() {
        return StateType.TAG;
    }
}
