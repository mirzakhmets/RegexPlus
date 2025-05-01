package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeSetLetter;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;

import java.util.HashSet;
import java.util.List;

public class NodeSetLetter extends Node {
    HashSet<Integer> set;

    public NodeSetLetter() {
        super();
        this.set = new HashSet<>();
    }

    @Override
    public NodeType getType() {
        return NodeType.SET_LETTER;
    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public List<INode> getChildren() {
        return null;
    }

    public char getLetter() {
        return '\0';
    }

    public HashSet<Integer> getSet() {
        return this.set;
    }

    @Override
    public boolean expand(IState[] start, IState[] finish) {
        if (!super.expand(start, finish)) {
            return false;
        }

        super.expand(start, finish);
        new EdgeSetLetter(start[0], finish[0], this.set);

        return true;
    }
}