package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.*;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeRepeatType;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;

public class NodeNot extends NodeGroup {
    Node nodeLeft;

    public NodeNot(INode group) {
        super(group);
        this.nodeLeft = new NodeRepeat(new NodeAnyLetter(),
                NodeRepeatType.STAR);
    }

    @Override
    public NodeType getType() {
        return NodeType.NOT;
    }

    @Override
    public boolean expand(IState[] start, IState[] finish) {
        //if (!super.expand(start, finish)) {
        //    return false;
        //}
 /*
 super.expand(start, finish);
 if (start == null || start.length == 0) {
 start = this.newEmptyState();
 }
 if (finish == null || finish.length == 0) {
 finish = this.newEmptyState();
 }
 */
        super.expand(start, finish);

        if (this.expanded) {
            new EdgeEmpty(start[0], this.expandedStates[0]);

            new EdgeEmpty(this.expandedStates[1], finish[0]);

            return false;
        }

        //if (start[0] == null) start[0] = new StateEmpty();
        //if (finish[0] == null) finish[0] = new StateEmpty();
        IState[] a = this.newEmptyState();
        IState[] b = this.newEmptyState();
        IState[] c = this.newEmptyState();
        IState[] d = this.newEmptyState();
        IState[] e = new IState[]{new StateMinus()};
        IState[] f = this.newTagState();
        ((StateTag) f[0]).pairedState = (State) e[0];
        new EdgeEmpty(start[0], f[0]);
        IEdge edgea = new EdgeEmptyNoList(f[0], a[0]);
        this.nodeLeft.expand(a, b);
        new EdgeEmpty(b[0], e[0]); //
        IEdge edgec = new EdgeEmptyNoList(f[0], c[0]);
        ((Node) this.group).expand(c, d);
        new EdgeEmpty(d[0], e[0]); //
        new EdgeEmpty(e[0], finish[0]);
        f[0].getOutputEdges().add(edgea);
        f[0].getOutputEdges().add(edgec);
        a[0].getInputEdges().add(edgea);
        c[0].getInputEdges().add(edgec);

        this.setExpanded();
        this.expandedStates[0] = f[0];
        this.expandedStates[1] = e[0];

        return true;
    }
}