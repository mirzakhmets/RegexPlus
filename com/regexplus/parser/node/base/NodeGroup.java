package com.regexplus.parser.node.base;

import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NodeGroup extends Node {
    INode group;

    public NodeGroup(INode group) {
        this.group = group;
    }

    public INode getGroup() {
        return this.group;
    }

    @Override
    public NodeType getType() {
        return NodeType.GROUP;
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public List<INode> getChildren() {
        return Collections.singletonList(this.group);
    }

    @Override
    public boolean expand(IState[] start, IState[] finish) {
        if (!super.expand(start, finish)) {
            return false;
        }

        super.expand(start, finish);
        ((Node) this.getGroup()).expand(start, finish);

        return true;
    }

    @Override
    public INode derivative() {
        return this.group.derivative();
    }

    @Override
    public INode derivative(char ch) {
        return this.group.derivative(ch);
    }
}
