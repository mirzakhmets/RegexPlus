package com.regexplus.parser.node.base;

import com.regexplus.Main;
import com.regexplus.automaton.base.EdgeEmpty;
import com.regexplus.automaton.base.StateAnd;
import com.regexplus.automaton.base.StateTag;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.model.State;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.common.NodeType;
import com.regexplus.parser.node.model.Node;
import com.regexplus.parser.node.model.NodePaired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

        this.setExpanded();
        this.expandedStates[0] = f[0];
        this.expandedStates[1] = e[0];

        return true;
    }

    @Override
    public INode derivative() {
        Set<INode> nodes = new HashSet<>();

        byte[] t = Main.alphabet.getBytes();

        for (byte i : t) {
            INode r = this.left.derivative((char) i);
            Set<INode> newNodes = new HashSet<>();

            if (r != null) {
                newNodes.add(r.clone());
            } else {
                continue;
            }

            r = this.right.derivative((char) i);

            if (r != null) {
                newNodes.add(r.clone());
            } else {
                continue;
            }

            INode p = null;

            for (INode j : newNodes) {
                if (p == null) {
                    p = j;
                } else {
                    p = new NodeAnd(j, p);
                }
            }

            if (p != null) {
                nodes.add(p);
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

        if (list.size() != 2) {
            return null;
        }

        if (list.size() == 2) {
            //if (list.getFirst() == this.left && list.getLast() == this.right) {
            //    return this;
            //}

            return new NodeAnd(list.getFirst(), list.getLast());
        }

        return list.getFirst();
    }

    @Override
    public INode clone() {
        return new NodeAnd(this.left.clone(), this.right.clone());
    }
}
