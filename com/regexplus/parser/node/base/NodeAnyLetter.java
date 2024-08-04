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
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);
        IState[] sta = newEmptyState();
        IState[] stb = newEmptyState();
        //new EdgeAnyLetter(start[0], finish[0]);
        new EdgeAnyLetter(sta[0], stb[0]);
        new EdgeEmpty(start[0], sta[0]);
        new EdgeEmpty(stb[0], finish[0]);
    }
}