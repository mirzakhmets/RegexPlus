package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.common.IState;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

public class NodeChoice extends NodePaired {
    public NodeChoice(INode left, INode right) {
        super(left, right);
    }

    @Override
    public NodeType getType() {
        return NodeType.CHOICE;
    }

    @Override
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);
        IState[] a = this.newEmptyState();
        IState[] b = this.newEmptyState();
        IState[] c = this.newEmptyState();
        IState[] d = this.newEmptyState();
        new EdgeEmpty(start[0], a[0]);
        ((Node) this.left).expand(a, b);
        new EdgeEmpty(b[0], finish[0]);
        new EdgeEmpty(start[0], c[0]);
        ((Node) this.right).expand(c, d);
        new EdgeEmpty(d[0], finish[0]);
    }
}
