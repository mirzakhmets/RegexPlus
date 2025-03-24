package com.regexplus.automaton.base;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;
import com.regexplus.automaton.model.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StateMinus extends State {
    //boolean[] visited;
    public Map<Integer, boolean[]> visited = new HashMap<>();
    int counterVisitIndex;
    boolean passedComputed = false, passed = false;

    public StateMinus() {
        super();
        this.counterVisitIndex = -1;
    }

    public StateType getType() {
        return StateType.MINUS;
    }

    public boolean visit(int visitIndex, IEdge edge) {
        //if (passedComputed) {
        //    return passed;
        //}

        if (this.counterVisitIndex != visitIndex) {
            this.counterVisitIndex = visitIndex;
            //this.visited = new boolean[this.getInputEdges().size()];
            //Arrays.fill(visited, false);
            this.visited.clear();
        }
        //visited[this.getInputEdges().size() - 1 - this.getInputEdges().indexOf(edge)] = true;
        //return !visited[0] && visited[1];
        for (Tag t : ((State) edge.getStart()).tags.values()) {
            if (t.finalState == this) {
                boolean[] v = null;
                if (!this.visited.containsKey(t.index)) {
                    v = new boolean[2];
                    Arrays.fill(v, false);
                    this.visited.put(t.index, v);
                } else {
                    v = this.visited.get(t.index);
                }
                v[t.type] = true;
                //v[this.getInputEdges().size() - 1 - this.getInputEdges().indexOf(edge)] = true;

                passedComputed = true;

                passed = !v[0] && v[1];

                return !v[0] && v[1];
            }
        }
        return false;
    }
}