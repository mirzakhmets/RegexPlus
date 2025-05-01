package com.regexplus.automaton.model;

import com.regexplus.automaton.base.*;
import com.regexplus.automaton.common.*;
import com.regexplus.match.common.IMatch;
import com.regexplus.match.model.Match;
import com.regexplus.parser.Parser;
import com.regexplus.parser.ParserInputStream;
import com.regexplus.parser.node.model.Node;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Automaton implements IAutomaton {
    IState start, finish;
    int currentStateIndex;

    public Automaton() {
        this.start = new StateStart();
        this.finish = new StateFinal();
        this.currentStateIndex = 0;
    }

    public IState getStart() {
        return this.start;
    }

    public IState getFinish() {
        return this.finish;
    }

    public void build(StringStream stream) {
        ParserInputStream pis = new ParserInputStream(new
                ByteArrayInputStream(stream.getSource().getBytes()));
        Parser parser = new Parser(pis);
        Node node = (Node) parser.Parse();
        IState[] astart = new IState[]{this.start};
        IState[] afinish = new IState[]{this.finish};
        node.expand(astart, afinish);
    }

    public void build(Node node) {
        IState[] astart = new IState[]{this.start};
        IState[] afinish = new IState[]{this.finish};
        node.expand(astart, afinish);
    }

    public void buildDerivative(StringStream stream) {
        ParserInputStream pis = new ParserInputStream(new
                ByteArrayInputStream(stream.getSource().getBytes()));
        Parser parser = new Parser(pis);
        Node node = (Node) parser.Parse();
        IState[] astart = new IState[]{this.start};
        IState[] afinish = new IState[]{this.finish};

        ((Node) node.derivative()).expand(astart, afinish);
    }

    public List<IMatch> match(IStream stream) {
        //int bestMatch = NO_MATCH;
        //int currentPosition = 0;
        Stack<State> stack = new Stack<>();
        List<IMatch> bestMatches = new ArrayList<>();
        stack.push((State) this.start);
        int index = 0;
        while (!stack.empty()) {
            State state = stack.pop();
            state.setIndex(++index);
            for (IEdge edge : state.getOutputEdges()) {
                State outState = (State) edge.getFinish();
                if (!outState.visited()) {
                    outState.setIndex(++index);
                    stack.push(outState);
                }
            }
        }
        //stack.push((State) this.start);
        //((State) this.start).setVisitIndex(++this.currentStateIndex);
        //boolean doContinue;
        int iterationsCount = 0;
        int currentIterationCount = 0;
 /*
 stack.push((State) this.start);
 ((State) this.start).setVisitIndex(this.currentStateIndex);
 ((State) this.start).renew();
 ((State) this.start).matches().add(new Match(stream,
stream.position()));
 */
        Set<Tag> allTags = new HashSet<>();
        do {
            Stack<State> closure = new Stack<>();
            Set<Tag> nextTags = new HashSet<>();
            ArrayList<Tag> tagsToDelete = new ArrayList<>();
            //++this.currentStateIndex;
            ++currentIterationCount;
            stack.push((State) this.start);
            ((State) this.start).setVisitIndex(this.currentStateIndex);
            ((State) this.start).renew();
            ((State) this.start).matches().add(new Match(stream,
                    stream.position()));
            //doContinue = false;
            while (!stack.empty()) {
                State state = stack.pop();
                //state.setVisitIndex(this.currentStateIndex);
                if (state.getType() == StateType.FINAL) {
                    //bestMatch = this.currentStateIndex;//currentPosition;
                    state.matches().get(0).setEnd(stream.position());
                    if (Match.isBetter(state.matches(), bestMatches)) {
                        bestMatches = state.matches();
                    }
                    //System.out.println("z: " + this.currentStateIndex);
                }
                if (state.getType() == StateType.TAG) {
                    Tag ta = new Tag();
                    Tag tb = new Tag();
                    ta.index = tb.index = ++Tag.TAG_INDEX;
                    ta.type = Tag.RIGHT;
                    tb.type = Tag.LEFT;
                    ta.pairedTag = tb;
                    tb.pairedTag = ta;
                    ta.finalState = ((StateTag) state).pairedState;
                    tb.finalState = ((StateTag) state).pairedState;
                    ((State) state.outputEdges.get(0).getFinish()).addTag(ta);
                    ((State) state.outputEdges.get(1).getFinish()).addTag(tb);
                    allTags.add(ta);
                    allTags.add(tb);
                }
                closure.push(state);
                allTags.addAll(state.tags.values());
// if (state.getIndex() == 5) {
// System.out.println("ZZ");
// }
                //System.out.println ("1: " + state.getIndex());
                for (IEdge edge : state.getOutputEdges()) {
                    if (edge.getType() == EdgeType.EMPTY) {
                        State outState = (State) edge.getFinish();
                        for (Tag t : state.tags.values()) {
                            if (t.finalState == outState) {
                                tagsToDelete.add(t);
                            }
                        }
                        boolean prePass = true;
                        outState.assignTags(state);
                        //allTags.addAll(state.tags.values());
                        if (outState.getType() == StateType.AND) {
                            StateAnd outStateAnd = (StateAnd) outState;
                            prePass = outStateAnd.visit(this.currentStateIndex, edge);
                            //allTags.addAll(state.tags.values());
                        }
                        if (outState.getType() == StateType.MINUS) {
                            StateMinus outStateMinus = (StateMinus) outState;
                            prePass = outStateMinus.visit(this.currentStateIndex,
                                    edge);
                            //allTags.addAll(state.tags.values());
                        }
                        //System.out.println("1: " + outState.getIndex());
                        if (outState.getVisitIndex() != this.currentStateIndex &&
                                prePass) {
                            outState.setVisitIndex(this.currentStateIndex);
                            //System.out.println("A: " + outState.getIndex() + " " + outState.getVisitIndex());
                            //outState.assignTags(state);
                            stack.push(outState);
                            //doContinue = true;
                            if (Match.isBetter(state.matches(), outState.matches())) {
                                outState.setMatches(state.matches());
                            }
                        }
                    }
                }
            }
            for (Tag t : tagsToDelete) {
                //t.remove();
                //t.pairedTag.remove();
            }
            ++this.currentStateIndex;
            ++currentIterationCount;
            //++currentPosition;
            //System.out.println("z: " + stream.current());
            while (!closure.empty()) {
                State state = closure.pop();
                //state.setVisitIndex(this.currentStateIndex);
                //if (state.getType() == StateType.FINAL) {
                // bestMatch = currentPosition;
                //}
                for (IEdge edge : state.getOutputEdges()) {
                    if (edge.getType() == EdgeType.LETTER) {
                        EdgeLetter edgeLetter = (EdgeLetter) edge;
                        //System.out.println("2: " + (char)edgeLetter.getLetter() + "" + (char)stream.current());
                        if (edgeLetter.accepts((char) stream.current())) {
                            State outState = (State) edgeLetter.getFinish();
                            //doContinue = true;
 /*
 boolean prePass = true;
 if (outState.getType() == StateType.AND) {
 StateAnd outStateAnd = (StateAnd) outState;
 prePass = outStateAnd.visit(this.currentStateIndex,
edge);
 }
 if (outState.getType() == StateType.MINUS) {
 StateMinus outStateMinus = (StateMinus) outState;
 prePass = outStateMinus.visit(this.currentStateIndex,
edge);
 }
 */
                            outState.assignTags(state);
                            nextTags.addAll(state.tags.values());
                            if (outState.getVisitIndex() != this.currentStateIndex/*&& prePass*/) {
                                outState.setVisitIndex(this.currentStateIndex);
                                stack.push(outState);
                                outState.setMatches(state.matches());
                                //outState.assignTags(state);
                                //nextTags.addAll(outState.tags.values());
                            }
                        }
                    }
                }
            }
            ArrayList<Tag> tagsRemove = new ArrayList<>();
            for (Tag t : allTags) {
                if (!nextTags.contains(t)) {
                    t.remove();
                    //t.pairedTag.remove();
                    tagsRemove.add(t);
                }
            }
            for (Tag t : tagsRemove) {
                allTags.remove(t);
            }
            //allTags.clear();
            //nextTags.clear();
            //++this.currentStateIndex;
            ++currentIterationCount;
            stream.next();
            if (stream.atEnd() || stack.empty()) {
                iterationsCount++;
            }
            //System.out.println("--");
        } while (iterationsCount <= 1);
        return bestMatches;
    }

    public boolean matches(IStream stream) {
        List<IMatch> result = this.match(stream);
        if (result.size() == 0) {
            return false;
        }
        return result.get(0).matches();
    }
}