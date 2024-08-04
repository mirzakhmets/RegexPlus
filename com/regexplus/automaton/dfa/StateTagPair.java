package com.regexplus.automaton.dfa;

import com.regexplus.automaton.model.State;
import com.regexplus.automaton.model.Tag;

import java.util.HashMap;
import java.util.Map;

public class StateTagPair {
    public State state;
    public Map<Integer, Tag> tags = new HashMap<Integer, Tag>();

    public StateTagPair(State state) {
        this.state = state;
    }
}