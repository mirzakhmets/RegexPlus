package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.base.EdgeEmptyNoList;
import com.regexplus.automaton.base.StateMinus;
import com.regexplus.automaton.base.StateTag;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

public class NodeMinus extends NodePaired {
    public NodeMinus(INode left, INode right) {
        super(left, right);
    }

    @Override
    public NodeType getType() {
        return NodeType.MINUS;
    }

    @Override
    public void expand(IState[] start, IState[] finish) {
        super.expand(start, finish);
        IState[] a = this.newEmptyState();
        IState[] b = this.newEmptyState();
        IState[] c = this.newEmptyState();
        IState[] d = this.newEmptyState();
        IState[] e = new IState[]{new StateMinus()};
        IState[] f = this.newTagState();
        ((StateTag) f[0]).pairedState = (State) e[0];
        new EdgeEmpty(start[0], f[0]);
        IEdge edgea = new EdgeEmptyNoList(f[0], a[0]);
        ((Node) this.left).expand(a, b);
        new EdgeEmpty(b[0], e[0]); //
        IEdge edgec = new EdgeEmptyNoList(f[0], c[0]);
        ((Node) this.right).expand(c, d);
        new EdgeEmpty(d[0], e[0]); //
        new EdgeEmpty(e[0], finish[0]);
        f[0].getOutputEdges().add(edgea);
        f[0].getOutputEdges().add(edgec);
        a[0].getInputEdges().add(edgea);
        c[0].getInputEdges().add(edgec);
    }
}