package com.regexplus.automaton.base;

import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.Edge;

public class EdgeLetter extends Edge {
    char letter;

    public EdgeLetter(IState start, IState finish, char letter) {
        super(start, finish);
        this.letter = letter;
    }

    @Override
    public EdgeType getType() {
        return EdgeType.LETTER;
    }

    public char getLetter() {
        return this.letter;
    }

    @Override
    public boolean accepts(char letter) {
        return this.letter == letter;
    }
}
