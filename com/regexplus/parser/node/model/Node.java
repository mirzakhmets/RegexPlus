package com.regexplus.parser.node.model;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.base.StateEmpty;
import com.regexplus.automaton.base.StateTag;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;

import java.util.List;

public class Node implements INode {
    int index = -1;
    boolean expanded = false;
    IState[] expandedStates = new IState[2];

    public Node() {
        expandedStates[0] = expandedStates[1] = null;
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

    public boolean expand(IState[] start, IState[] finish) {
        /*
        if (expanded){
            if (start[0] == null) {
                start[0] = expandedStates[0];
            } else {
                new EdgeEmpty(start[0], expandedStates[0]);
            }

            if (finish[0] == null) {
                finish[0] = expandedStates[1];
            } else {
                new EdgeEmpty(expandedStates[1], finish[0]);
            }

            return false;
        }

        if (expandedStates[0] == null) {
            expandedStates[0] = new StateEmpty();
        }

        if (expandedStates[1] == null) {
            expandedStates[1] = new StateEmpty();
        }

        if (start[0] == null) {
            start[0] = expandedStates[0];
        } else {
            new EdgeEmpty(start[0], expandedStates[0]);
        }

        if (finish[0] == null) {
            finish[0] = expandedStates[1];
        } else {
            new EdgeEmpty(expandedStates[1], finish[0]);
        }

        return expanded = true;*/

        if (start[0] == null) {
            start[0] = new StateEmpty();//this.newEmptyState(); //expandedStates[0];
        } else {
            //new EdgeEmpty(start[0], expandedStates[0]);
        }

        if (finish[0] == null) {
            finish[0] = new StateEmpty(); //expandedStates[1];
        } else {
            //new EdgeEmpty(expandedStates[1], finish[0]);
        }

        return true;
    }

    protected IState[] newTagState() {
        return new IState[]{new StateTag()};
    }

    public INode derivative() {
        return null;
    }

    public INode derivative(char ch) {
        return null;
    }
}
