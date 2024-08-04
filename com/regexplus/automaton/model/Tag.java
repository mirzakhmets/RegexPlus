package com.regexplus.automaton.model;

import java.util.HashSet;
import java.util.Set;

public class Tag {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static int TAG_INDEX = -1;
    public int index = -1;
    public int type = LEFT;
    public Tag pairedTag = null;
    public State finalState = null;
    public Set<State> states = new HashSet<>();

    public Tag() {
    }

    public void remove() {
        for (State state : states) {
            state.tags.remove(this.index);
        }
    }

    @Override
    public int hashCode() {
        return (this.index << 1) | this.type;
    }
}