package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeRepeatType;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NodeRepeat extends Node {
    public final int REPEAT_MIN = -1;
    public final int REPEAT_MAX = -1;
    INode node;
    int min, max;
    NodeRepeatType repeatType;

    public NodeRepeat(INode node, int min, int max) {
        this.node = node;
        this.min = min;
        this.max = max;
        this.repeatType = NodeRepeatType.USER_DEFINED;
    }

    public NodeRepeat(INode node, NodeRepeatType repeatType) {
        this.node = node;
        this.repeatType = repeatType;
    }

    @Override
    public NodeType getType() {
        return NodeType.REPEAT;
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public List<INode> getChildren() {
        return Collections.singletonList(this.node);
    }

    public INode getNode() {
        return this.node;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public NodeRepeatType getRepeatType() {
        return this.repeatType;
    }

    @Override
    public boolean expand(IState[] start, IState[] finish) {
        if (!super.expand(start, finish)) {
            return false;
        }

        super.expand(start, finish);
        IState[] a = this.newEmptyState();
        IState[] b = this.newEmptyState();
        new EdgeEmpty(start[0], a[0]);
        ((Node) this.node).expand(a, b);
        switch (this.getRepeatType()) {
            case STAR -> {
                new EdgeEmpty(a[0], b[0]);
                new EdgeEmpty(b[0], a[0]);
            }
            case PLUS -> new EdgeEmpty(b[0], a[0]);
            case QUESTION -> new EdgeEmpty(a[0], b[0]);
        }
        new EdgeEmpty(b[0], finish[0]);

        return true;
    }
}