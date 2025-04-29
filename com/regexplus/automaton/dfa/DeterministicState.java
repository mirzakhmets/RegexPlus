package com.regexplus.automaton.dfa;

import com.regexplus.automaton.base.StateAnd;
import com.regexplus.automaton.base.StateMinus;
import com.regexplus.automaton.base.StateTag;
import com.regexplus.automaton.common.EdgeType;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;
import com.regexplus.automaton.model.Tag;
import com.regexplus.match.common.IMatch;
import com.regexplus.match.model.Match;

import java.io.OutputStream;
import java.util.*;

public class DeterministicState {
    public static final int LETTER_MAX = 256;
    public static int VISIT_INDEX = -1;
    public static int NFA_VISIT_INDEX = -1;
    public int index;
    public Set<State> states = new HashSet<>();
    public DeterministicState[] transitions = new
            DeterministicState[LETTER_MAX];
    public List<StateTagPair> tags = new ArrayList<>();
    public boolean isFinal = false;
    public DeterministicAutomaton automaton;
    public int visitIndex = -1;
    public List<IMatch> matches = new ArrayList<>();
    public List<State> orderedStates = new ArrayList<>();
    public List<InputEdge> inputEdges = new ArrayList<>();

    public DeterministicState(DeterministicAutomaton automaton) {
        this.index = -1;
        this.automaton = automaton;
    }

    public boolean closure() {
        for (State s : this.automaton.nfaStates) {
            s.tags.clear();
        }
        for (StateTagPair tag : this.tags) {
            tag.state.tags.putAll(tag.tags);
        }
        Stack<State> stack = new Stack<>();
        ++NFA_VISIT_INDEX;
        Collections.reverse(this.orderedStates);
        for (State s : this.orderedStates) {
            stack.push(s);
            s.setVisitIndex(NFA_VISIT_INDEX);
        }

        Set<StateAnd> andStates = new HashSet<>();

        while (!stack.empty()) {
            State state = stack.pop();
            if (!this.states.contains(state)) {
                this.states.add(state);
                this.orderedStates.add(state);
            }
            if (state.getType() == StateType.FINAL) {
                this.isFinal = true;
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
                //((State) state.getOutputEdges().get(0).getFinish()).addTag(ta);
                //((State) state.getOutputEdges().get(1).getFinish()).addTag(tb);
                StateTagPair paira = new StateTagPair(((State)
                        state.getOutputEdges().get(0).getFinish()));
                StateTagPair pairb = new StateTagPair(((State)
                        state.getOutputEdges().get(1).getFinish()));
                paira.tags.put(ta.index, ta);
                pairb.tags.put(tb.index, tb);
                this.tags.add(paira);
                this.tags.add(pairb);
                ((State) state.getOutputEdges().get(0).getFinish()).addTag(ta);
                ((State) state.getOutputEdges().get(1).getFinish()).addTag(tb);
            }
            for (IEdge edge : state.getOutputEdges()) {
                if (edge.getType() == EdgeType.EMPTY && ((State)
                        edge.getFinish()).getVisitIndex() != NFA_VISIT_INDEX
                        && !this.states.contains((State) edge.getFinish())) {
                    State outState = (State) edge.getFinish();
                    boolean prePass = true;
                    outState.assignTags(state);
                    StateTagPair pair = new StateTagPair(outState);
                    pair.tags.putAll(outState.tags);
                    this.tags.add(pair);
                    if (outState.getType() == StateType.AND) {
                        StateAnd outStateAnd = (StateAnd) outState;
                        prePass = outStateAnd.visit(NFA_VISIT_INDEX, edge);

                        if (!andStates.contains(outStateAnd)) {
                            andStates.add(outStateAnd);
                        } else {
                            if (!prePass) {
                                //System.out.println("UNSATSIFIABLE");

                                //System.exit(0);

                                return false;
                            }
                        }
                    }
                    if (outState.getType() == StateType.MINUS) {
                        StateMinus outStateMinus = (StateMinus) outState;
                        prePass = outStateMinus.visit(NFA_VISIT_INDEX, edge);
                    }
                    if (outState.getVisitIndex() != NFA_VISIT_INDEX &&
                            prePass) {
                        outState.setVisitIndex(NFA_VISIT_INDEX);
                        stack.push(outState);
                        if (Match.isBetter(state.matches(), outState.matches())) {
                            outState.setMatches(state.matches());
                        }
                    }
                }
            }
        }

        /*
        for (StateAnd sa : andStates) {
            if (!(sa.visitedAll[0] && sa.visitedAll[1])) {
                return false;
            }
        }*/

        return true;
    }

    public DeterministicState step(char symbol) {
        //List<State> result = new ArrayList<>();
        DeterministicState deterministicState = new
                DeterministicState(this.automaton);
        //List<StateTagPair> pairs = new ArrayList<>();
        ++NFA_VISIT_INDEX;
        for (State s : this.orderedStates) {
            for (IEdge e : s.getOutputEdges()) {
                if (e.getType() == EdgeType.LETTER && e.accepts(symbol)) {
                    if (((State) e.getFinish()).getVisitIndex() !=
                            NFA_VISIT_INDEX && !deterministicState.states.contains((State)
                            e.getFinish())) {
                        ((State) e.getFinish()).setVisitIndex(NFA_VISIT_INDEX);
                        StateTagPair pair = new StateTagPair(((State)
                                e.getFinish()));
                        pair.tags.putAll(s.tags);
                        //this.tags.add(pair);
                        //pairs.add(pair);
                        deterministicState.tags.add(pair);
                        //result.add(((State) e.getFinish()));
                        deterministicState.states.add((State) e.getFinish());
                        deterministicState.orderedStates.add((State) e.getFinish());
                    }
                }
            }
        }
        if (deterministicState.states.size() == 0) {
            return null;
        }
        //deterministicState.states.addAll(result);
        //deterministicState.orderedStates = result;
        //deterministicState.tags.addAll(pairs);
        if (!deterministicState.closure()) {
            System.out.println("UNSATISFIABLE");

            System.exit(0);
            //return null;
        }
 /*
 if (!deterministicState.automaton.states.contains(deterministicState)) {
 deterministicState.index =
deterministicState.automaton.states.size();
 deterministicState.automaton.states.add(deterministicState);
 return deterministicState;
 }*/
        for (DeterministicState s : this.automaton.states) {
            if (s.hashCode() == deterministicState.hashCode()) {
                return s;
            }
        }
        deterministicState.index = deterministicState.automaton.states.size();
        deterministicState.automaton.states.add(deterministicState);
        return deterministicState;
        //return null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (State state : this.states) {
            result = (result << 12) ^ state.getIndex() ^ (result >> 20);
            //result = (result * 257 + state.getIndex()) % 100001;
        }
        return result;
    }

    public List<IMatch> matches() {
        return this.matches;
    }

    public void setMatches(List<IMatch> matches) {
        this.matches = new ArrayList<>();
        for (IMatch match : matches) {
            this.matches.add(match.copy());
        }
        //this.matches = matches;
    }

    public String label() {
        String result = "";
        for (State s : this.orderedStates) {
            if (result.length() > 0) {
                result += " ";
            }
            result += "" + s.getIndex();
        }
        return result;
    }

    public void write(OutputStream stream) {
        try {
            if (this.isFinal) {
                stream.write(("node_" + this.index + " [shape=doublecircle,label=\"" + this.index /*+ ": " + this.label()*/ + "\"];\n").getBytes());
            } else {
                stream.write(("node_" + this.index + " [shape=circle,label=\"" + this.index /*+ ": " + this.label()*/ + "\"];\n").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}