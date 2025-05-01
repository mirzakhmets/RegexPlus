package com.regexplus.parser.node.base;

import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

import java.util.ArrayList;

public class NodeChoice extends NodePaired {
    public int logicalChoiceIndex = -1;

    public NodeChoice(INode left, INode right) {
        super(left, right);
    }

    @Override
    public NodeType getType() {
        return NodeType.CHOICE;
    }

    @Override
    public boolean expand(IState[] start, IState[] finish) {
        if (!super.expand(start, finish)) {
            return false;
        }

        super.expand(start, finish);
        IState[] a = this.newEmptyState();
        IState[] b = this.newEmptyState();
        IState[] c = this.newEmptyState();
        IState[] d = this.newEmptyState();
        new EdgeEmpty(start[0], a[0]);

        ((State) a[0]).logicalChoiceIndex = this.logicalChoiceIndex;

        ((Node) this.left).expand(a, b);
        new EdgeEmpty(b[0], finish[0]);
        new EdgeEmpty(start[0], c[0]);
        ((Node) this.right).expand(c, d);

        ((State) c[0]).logicalChoiceIndex = this.logicalChoiceIndex;

        new EdgeEmpty(d[0], finish[0]);

        return true;
    }

    @Override
    public INode derivative(char ch) {
        ArrayList<INode> list = new ArrayList<>();

        INode r = this.left.derivative(ch);

        if (r != null) {
            list.add(r);
        }

        r = this.right.derivative(ch);

        if (r != null) {
            list.add(r);
        }

        if (list.isEmpty()) {
            return null;
        }

        if (list.size() == 2) {
            if (list.getFirst() == this.left && list.getLast() == this.right) {
                return this;
            }

            return new NodeChoice(list.getFirst(), list.getLast());
        }

        return list.getFirst();
    }
}
