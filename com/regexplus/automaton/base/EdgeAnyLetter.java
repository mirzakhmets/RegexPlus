package com.regexplus.automaton.base;

import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IState;

public class EdgeAnyLetter extends EdgeLetter {
    public EdgeAnyLetter(IState start, IState finish) {
        super(start, finish, '.');
    }

    @Override
    public EdgeType getType() {
        return EdgeType.LETTER;
    }

    @Override
    public boolean accepts(char letter) {
        return true;
    }
}
