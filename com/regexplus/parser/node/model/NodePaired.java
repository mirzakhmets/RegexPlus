package com.regexplus.parser.node.model;

import com.regexplus.parser.node.common.INode;

import java.util.Arrays;
import java.util.List;

public class NodePaired extends Node {
    protected INode left, right;

    public NodePaired(INode left, INode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public List<INode> getChildren() {
        return Arrays.asList(this.left, this.right);
    }
}