package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeAnyLetter;
import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;

import java.util.List;

public class NodeAnyLetter extends Node {
    public NodeAnyLetter() {
        super();
    }

    @Override
    public NodeType getType() {
        return NodeType.ANY_LETTER;
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

    @Override
    public boolean expand(IState[] start, IState[] finish) {
        //if (!super.expand(start, finish)) {
        //    return false;
        //}

        super.expand(start, finish);

        if (this.expanded) {
            new EdgeEmpty(start[0], this.expandedStates[0]);

            new EdgeEmpty(this.expandedStates[1], finish[0]);

            return false;
        }

        IState[] sta = newEmptyState();
        IState[] stb = newEmptyState();
        //new EdgeAnyLetter(start[0], finish[0]);
        new EdgeAnyLetter(sta[0], stb[0]);
        new EdgeEmpty(start[0], sta[0]);
        new EdgeEmpty(stb[0], finish[0]);

        this.setExpanded();
        this.expandedStates[0] = sta[0];
        this.expandedStates[1] = stb[0];

        return true;
    }

    @Override
    public INode derivative() {
        return this;
    }

    @Override
    public INode derivative(char ch) {
        return new NodeLetter(ch);
    }
}