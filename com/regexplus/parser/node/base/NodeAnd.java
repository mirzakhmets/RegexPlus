package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.base.StateAnd;
import com.regexplus.automaton.base.StateTag;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

public class NodeAnd extends NodePaired {
    public NodeAnd(INode left, INode right) {
        super(left, right);
    }

    @Override
    public NodeType getType() {
        return NodeType.AND;
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
        IState[] b = this.newEmptyState();
        IState[] c = this.newEmptyState();
        IState[] d = this.newEmptyState();
        IState[] e = new IState[]{new StateAnd()};
        //IState[] f = this.newEmptyState();
        IState[] f = this.newTagState();
        ((StateTag) f[0]).pairedState = (State) e[0];
        new EdgeEmpty(start[0], f[0]);
        new EdgeEmpty(f[0], a[0]);
        ((Node) this.left).expand(a, b);
        new EdgeEmpty(b[0], e[0]);
        new EdgeEmpty(f[0], c[0]);
        ((Node) this.right).expand(c, d);
        new EdgeEmpty(d[0], e[0]);
        new EdgeEmpty(e[0], finish[0]);

        this.expanded = true;
        this.expandedStates[0] = f[0];
        this.expandedStates[1] = e[0];

        return true;
    }
}
