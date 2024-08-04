package com.regexplus.test;

import com.regexplus.automaton.base.EdgeLetter;
import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.dfa.DeterministicAutomaton;
import com.regexplus.automaton.model.Automaton;
import com.regexplus.automaton.model.Edge;
import com.regexplus.automaton.model.State;
import com.regexplus.automaton.model.StringStream;
import com.regexplus.match.common.IMatch;
import com.regexplus.match.model.Match;
import com.regexplus.parser.node.base.NodeLetter;
import com.regexplus.parser.node.base.NodeRepeat;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.model.Node;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Case {
    String pattern;
    List<CaseResult> tests;

    public Case(String pattern, CaseResult[] tests) {
        this.pattern = pattern;
        this.tests = Arrays.asList(tests);
    }

    public static void writeState(BufferedWriter bw, IState state) {
        try {
            int index = 0;
            bw.write("digraph G {\n");
            Stack<IState> stack = new Stack<>();
            stack.push(state);
            ((State) state).setIndex(++index);
            while (!stack.empty()) {
                IState current = stack.pop();
                switch (current.getType()) {
                    case START -> bw.write("\tnode_" + ((State)
                            current).getIndex() + " [label=\"$ " + ((State) current).getIndex() + "\"]\n");
                    case EMPTY -> bw.write("\tnode_" + ((State)
                            current).getIndex() + " [label=\"" + ((State) current).getIndex() + "\"]\n");
                    case FINAL -> bw.write("\tnode_" + ((State)
                            current).getIndex() + " [label=\"^ " + ((State) current).getIndex() + "\"]\n");
                    case AND -> bw.write("\tnode_" + ((State) current).getIndex()
                            + " [label=\"& " + ((State) current).getIndex() + "\"]\n");
                    case MINUS -> bw.write("\tnode_" + ((State)
                            current).getIndex() + " [label=\"- " + ((State) current).getIndex() + "\"]\n");
                }
                List<IEdge> edges = current.getOutputEdges();
                for (IEdge edge : edges) {
                    Edge edge2 = (Edge) edge;
                    if (!((State) edge2.getFinish()).visited()) {
                        stack.push(edge2.getFinish());
                        ((State) edge2.getFinish()).setIndex(++index);
                    }
                    bw.write("\t\tnode_" + ((State) current).getIndex() + " -> node_" + ((State) edge2.getFinish()).getIndex());
                    if (edge2.getType() == EdgeType.LETTER) {
                        bw.write("[label = \"" + ((EdgeLetter) edge2).getLetter() +
                                "\"]");
                    }
                    bw.write("\n");
                }
            }
            bw.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeNode(BufferedWriter bw, INode node) {
        try {
            int index = 0;
            bw.write("digraph G {\n");
            Stack<INode> stack = new Stack<>();
            stack.push(node);
            ((Node) node).setIndex(++index);
            while (!stack.empty()) {
                INode current = stack.pop();
                switch (current.getType()) {
                    case CHOICE -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + " [label=\"|\"]\n");
                    case CONCAT -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + " [label=\"*\"]\n");
                    case LETTER -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + "[label=\"" + ((NodeLetter) current).getLetter() +
                            "\"]\n");
                    case GROUP -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + " [label=\"()\"]\n");
                    case REPEAT -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + " [label=\"R:" +
                            ((NodeRepeat) current).getRepeatType().toString()
                            + "\"]\n");
                    case AND -> bw.write("\tnode_" + ((Node) current).getIndex()
                            + " [label=\"&\"]\n");
                    case MINUS -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + " [label=\"-\"]\n");
                    case ANY_LETTER -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + "[label=\".\"]\n");
                    case NOT -> bw.write("\tnode_" + ((Node) current).getIndex()
                            + "[label=\"~\"]\n");
                    case SET_LETTER -> bw.write("\tnode_" + ((Node)
                            current).getIndex() + "[label=\"[]\"]\n");
                }
                if (!current.isAtomic()) {
                    List<INode> children = current.getChildren();
                    for (INode child : children) {
                        Node childNode = (Node) child;
                        if (!childNode.visited()) {
                            stack.push(child);
                            childNode.setIndex(++index);
                        }
                        bw.write("\t\tnode_" + ((Node) current).getIndex() + " -> node_" + childNode.getIndex() + "\n");
                    }
                }
            }
            bw.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        System.out.println("Case: " + this.pattern);
        boolean allTests = true;
        for (CaseResult caseResult : tests) {
            Automaton automaton = new Automaton();
            automaton.build(new StringStream(this.pattern));
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(new
                        File("C:/Debug/pattern.gv")));
                writeState(bw, automaton.getStart());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String string = caseResult.getString();
            DeterministicAutomaton deterministicAutomaton = new
                    DeterministicAutomaton(automaton);
            try {
                FileOutputStream fos = new
                        FileOutputStream("C:/debug/dfa.gv");
                deterministicAutomaton.write(fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<IMatch> matches = deterministicAutomaton.match(new
                    StringStream(string)); //;automaton.match(new StringStream(string));
            int length = matches.size() == 0 ? Match.NO_MATCH :
                    matches.get(0).length();
            //if (matches.size() > 0) {
            // System.out.println(matches.get(0).start() + " " + matches.get(0).length());
            //}
            System.out.println("\t" + string + " " + " " + length + " " +
                    caseResult.getResult()
                    + " " + ((caseResult.getResult() == length) ? "true" : "false"));
            if (caseResult.getResult() != length) {
                allTests = false;
            }
        }
        System.out.println("===" + (allTests ? "ALL TESTS PASSED" : "") +
                "===");
    }
}