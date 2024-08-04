package com.regexplus.parser.node.model;

import com.regexplus.automaton.base.StateEmpty;
import com.regexplus.automaton.base.StateTag;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;

import java.util.List;

public class Node implements INode {
    int index = -1;

    public Node() {
    }

    public NodeType getType() {
        return NodeType.CONCAT;
    }

    public boolean isAtomic() {
        return false;
    }

    public List<INode> getChildren() {
        return null;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean visited() {
        return this.getIndex() != -1;
    }

    protected IState[] newEmptyState() {
        return new IState[]{new StateEmpty()};
    }

    public void expand(IState[] start, IState[] finish) {
        if (start[0] == null) start[0] = new StateEmpty();
        if (finish[0] == null) finish[0] = new StateEmpty();
    }

    protected IState[] newTagState() {
        return new IState[]{new StateTag()};
    }
}
