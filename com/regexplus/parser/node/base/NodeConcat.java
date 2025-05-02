package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

import java.util.ArrayList;

public class NodeConcat extends NodePaired {
    public NodeConcat(INode left, INode right) {
        super(left, right);
    }

    @Override
    public NodeType getType() {
        return NodeType.CONCAT;
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

        IState[] a = this.newEmptyState();
        IState[] b = newEmptyState();
        IState[] c = newEmptyState();
        IState[] d = newEmptyState();
        //((Node) this.left).expand(start, a);
        //((Node) this.right).expand(a, finish);
        ((Node) this.left).expand(a, b);
        ((Node) this.right).expand(c, d);
        new EdgeEmpty(start[0], a[0]);
        new EdgeEmpty(b[0], c[0]);
        new EdgeEmpty(d[0], finish[0]);

        this.setExpanded();
        this.expandedStates[0] = a[0];
        this.expandedStates[1] = d[0];

        return true;
    }

    @Override
    public INode derivative() {
        INode r = this.left.derivative();

        if (r == null) {
            return null;
        }

        if (r == this.left) {
            return this;
        }

        // r = r.clone();
        return new NodeConcat(r, this.right/*.clone()*/);
    }

    @Override
    public INode derivative(char ch) {
        INode r = this.left.derivative(ch);

        if (r == null) {
            return null;
        }

        if (r == this.left) {
            return this;
        }

        // r = r.clone()
        return new NodeConcat(r, this.right/*.clone()*/);
    }

    @Override
    public INode clone() {
        return new NodeConcat(this.left.clone(), this.right.clone());
    }
}
