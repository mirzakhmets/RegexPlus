package com.regexplus.automaton.base;

import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IState;

import java.util.HashSet;

public class EdgeSetLetter extends EdgeLetter {
    HashSet<Integer> set;

    public EdgeSetLetter(IState start, IState finish, HashSet<Integer> set) {
        super(start, finish, '\0');
        this.set = set;
    }

    @Override
    public EdgeType getType() {
        return EdgeType.LETTER;
    }

    @Override
    public boolean accepts(char letter) {
        return this.set.contains((int) letter);
    }
}
