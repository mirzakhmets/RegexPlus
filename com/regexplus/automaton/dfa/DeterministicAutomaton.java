package com.regexplus.automaton.dfa;

import com.regexplus.Main;
import com.regexplus.automaton.base.StateAnd;
import com.regexplus.automaton.base.StateMinus;
import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IStream;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.Automaton;
import com.regexplus.automaton.model.State;
import com.regexplus.match.common.IMatch;
import com.regexplus.match.model.Match;

import java.io.OutputStream;
import java.util.*;

public class DeterministicAutomaton {
    public Set<DeterministicState> states = new HashSet<>();
    public Set<State> nfaStates = new HashSet<>();
    public DeterministicState start;
    public Map<Integer, DeterministicState> statesIndex = new
            HashMap<>();
    public Map<Integer, State> nfaStatesIndex = new HashMap<>();
    public DeterministicAutomaton(Automaton automaton) {
        Stack<State> stack = new Stack<>();
        stack.push((State) automaton.getStart());
        int index = 0;
        while (!stack.empty()) {
            State state = stack.pop();
            state.setIndex(++index);
            this.nfaStatesIndex.put(index, state);
            this.nfaStates.add(state);
            for (IEdge edge: state.getOutputEdges()) {
                State outState = (State) edge.getFinish();
                if (!outState.visited()) {
                    outState.setIndex(++index);
                    stack.push(outState);
                }
            }
        }
        this.start = new DeterministicState(this);
        this.start.states.add((State) automaton.getStart());
        this.start.orderedStates.add((State) automaton.getStart());
        this.start.closure();
        this.start.index = this.states.size();
        this.states.add(this.start);
        this.statesIndex.put(this.start.index, this.start);
        Stack<DeterministicState> detStack = new Stack<>();
        detStack.push(this.start);
        this.start.visitIndex = ++DeterministicState.VISIT_INDEX;
        while (!detStack.empty()) {
            DeterministicState state = detStack.pop();

            byte[] t = Main.alphabet.getBytes();

            for (byte i : t) {
                // tags;
                for (State s: this.nfaStates) {
                    s.tags.clear();
                }
                // tags;

                for (State s: this.nfaStates) {
                    if (s.getType() == StateType.AND) {
                        ((StateAnd) s).visited.clear();
                    }
                    if (s.getType() == StateType.MINUS) {
                        ((StateMinus) s).visited.clear();
                    }
                }

                // tags;
                for (StateTagPair tag: state.tags) {
                    tag.state.tags.putAll(tag.tags);
                }
                // tags;

                DeterministicState s = state.step((char) i);

                if (s != null && s.isFinal) {
                    System.out.println("SATISFIABLE: " + s.index);

                    System.exit(0);
                }

                state.transitions[i] = s;
                if (s != null && s.visitIndex !=
                        DeterministicState.VISIT_INDEX) {
                    detStack.push(s);

                    s.level = state.level + 1;

                    s.visitIndex = DeterministicState.VISIT_INDEX;

                    //System.out.println(s.level + " " + this.states.size());
                }
            }
        }
    }
    public List<IMatch> match(IStream stream) {
        Stack<DeterministicState> stack = new Stack<>();
        List<IMatch> bestMatches = new ArrayList<>();
        if (stream.atEnd() && this.start.isFinal) {
            bestMatches.add(new Match(stream, 0));
            bestMatches.get(0).setEnd(0);
            return bestMatches;
        }
        ++DeterministicState.VISIT_INDEX;
        int finalIterations = 0;
        stack.push(this.start);
        do {
            if (this.start.visitIndex != DeterministicState.VISIT_INDEX) {
                this.start.visitIndex = DeterministicState.VISIT_INDEX;
                //stack.push(this.start);
                this.start.matches = new ArrayList<>();
                this.start.matches().add(new Match(stream, stream.position()));
            }
            //while (!stream.atEnd()) {
            List<DeterministicState> current = new ArrayList<>();
            while (!stack.empty()) {
                DeterministicState state = stack.pop();
                DeterministicState nextState = null;
                if (stream.current() != -1) {
                    nextState = state.transitions[stream.current()];
                }
                if (nextState != null) {
                    if (nextState.visitIndex != DeterministicState.VISIT_INDEX)
                    {
                        nextState.visitIndex = DeterministicState.VISIT_INDEX;
                        current.add(nextState);
                        nextState.setMatches(state.matches());
                        if (nextState.isFinal) {
                            nextState.matches().get(0).setEnd(stream.position() + 1);
                            if (Match.isBetter(nextState.matches(), bestMatches)) {
                                //bestMatches = nextState.matches();

                                bestMatches = new ArrayList<>();

                                for (IMatch m: nextState.matches()) {
                                    bestMatches.add(m.copy());
                                }
                            }

                            //nextState.matches().get(0).setEnd(-1);
                        }
                    } else if (Match.isBetter(state.matches(), nextState.matches()))
                    {
                        nextState.setMatches(state.matches());


                        nextState.matches = new ArrayList<>();

                        for (IMatch m: state.matches()) {
                            nextState.matches.add(m.copy());
                        }
                    }

                    /*if (nextState.isFinal) {
                        if (stream.atEnd()) {
                            nextState.matches().get(0).setEnd(stream.position());
                        } else {
                            nextState.matches().get(0).setEnd(stream.position());
                        }
                        if (Match.isBetter(nextState.matches(), bestMatches)) {
bestMatches = nextState.matches();
                        }
                    }*/
                }
            }
            stack.push(this.start);
            Collections.reverse(current);
            for (DeterministicState s: current) {
                stack.push(s);
            }
            stream.next();
            ++DeterministicState.VISIT_INDEX;
            if (stream.atEnd() /*|| stack.empty()*/) {
                ++finalIterations;
            } //else {
            //finalIterations = 0;
            //}
        } while (finalIterations <= 1);
        /*
        while (!stack.empty()) {
            DeterministicState state = stack.pop();
            if (state.isFinal) {
                state.matches().get(0).setEnd(stream.position());
                if (Match.isBetter(state.matches(), bestMatches)) {
                    bestMatches = state.matches();
                }
            }
        }*/
        if (this.start.isFinal && bestMatches.size() == 0) {
            bestMatches.add(new Match(stream, 0));
            bestMatches.get(0).setEnd(0);
        }
        return bestMatches;
    }
    public boolean matches(IStream stream) {
        List<IMatch> result = this.match(stream);
        if (result.size() == 0) {
            return false;
        }
        return result.get(0).matches();
    }
    public void write(OutputStream stream) {
        try {
            stream.write("digraph G {\n".getBytes());
            for (DeterministicState s : this.states) {
                s.write(stream);

                byte[] t = Main.alphabet.getBytes();

                for (byte i : t) {
                    if (s.transitions[i] != null) {
                        try {
                            stream.write(("\tnode_" + s.index + " -> node_" +
                                    s.transitions[i].index + " [label=\"" + ((char) i) + "\"];\n").getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            stream.write("}\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
