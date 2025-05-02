package com.regexplus.parser.node.base;

import com.regexplus.Main;
import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        new EdgeEmpty(start[0], a[0]);

        ((State) a[0]).logicalChoiceIndex = this.logicalChoiceIndex;

        ((Node) this.left).expand(a, b);
        new EdgeEmpty(b[0], finish[0]);
        new EdgeEmpty(start[0], c[0]);
        ((Node) this.right).expand(c, d);

        ((State) c[0]).logicalChoiceIndex = this.logicalChoiceIndex;

        new EdgeEmpty(d[0], finish[0]);

        this.setExpanded();
        this.expandedStates[0] = a[0];
        this.expandedStates[1] = d[0];

        return true;
    }

    @Override
    public INode derivative() {
        Set<INode> nodes = new HashSet<>();

        byte[] t = Main.alphabet.getBytes();

        for (byte i : t) {
            INode r = this.left.derivative((char) i);

            if (r != null) {
                nodes.add(r.clone());
            }

            r = this.right.derivative((char) i);

            if (r != null) {
                nodes.add(r.clone());
            }
        }

        INode result = null;

        for (INode node : nodes) {
            if (result == null) {
                result = node;
            } else {
                result = new NodeChoice(node, result);
            }
        }

        return result;
    }

    @Override
    public INode derivative(char ch) {
        ArrayList<INode> list = new ArrayList<>();

        INode r = this.left.derivative(ch);

        if (r != null) {
            list.add(r.clone());
        }

        r = this.right.derivative(ch);

        if (r != null) {
            list.add(r.clone());
        }

        if (list.isEmpty()) {
            return null;
        }

        if (list.size() == 2) {
            //if (list.getFirst() == this.left && list.getLast() == this.right) {
            //    return this;
            //}

            return new NodeChoice(list.getFirst(), list.getLast());
        }

        return list.getFirst();
    }

    @Override
    public INode clone() {
        return new NodeChoice(this.left.clone(), this.right.clone());
    }
}
