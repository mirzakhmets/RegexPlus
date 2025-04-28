package com.regexplus.automaton.model;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.common.StateType;
import com.regexplus.match.common.IMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State implements IState {
    public Map<Integer, Tag> tags = new HashMap<Integer, Tag>();
    int index, visitIndex;
    ArrayList<IEdge> inputEdges, outputEdges;
    List<IMatch> matches;
    public int logicalChoiceIndex  = -1;

    public State() {
        this.index = -1;
        this.visitIndex = -1;
        this.inputEdges = new ArrayList<>();
        this.outputEdges = new ArrayList<>();
        this.renew();
    }

    public StateType getType() {
        return StateType.EMPTY;
    }

    public List<IEdge> getInputEdges() {
        return this.inputEdges;
    }

    public List<IEdge> getOutputEdges() {
        return this.outputEdges;
    }

    public void addEdge(IEdge edge) {
        this.getOutputEdges().add(edge);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean visited() {
        return this.index != -1;
    }

    public int getVisitIndex() {
        return this.visitIndex;
    }

    public void setVisitIndex(int visitIndex) {
        this.visitIndex = visitIndex;
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

    public void renew() {
        this.matches = new ArrayList<>();
    }

    public void assignTags(State state) {
        for (Tag t : state.tags.values()) {
            if (t.finalState != this && !this.tags.containsKey(t.index)) {
                this.addTag(t);
            }
        }
    }

    public void addTag(Tag tag) {
        this.tags.put(tag.index, tag);
        tag.states.add(this);
    }

    @Override
    public int hashCode() {
        return this.index;
    }
}