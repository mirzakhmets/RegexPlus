package com.regexplus.parser.node.common;

import java.util.List;

public interface INode {
    NodeType getType();

    boolean isAtomic();

    List<INode> getChildren();

    INode derivative();

    INode derivative(char ch);

    INode clone();
}
