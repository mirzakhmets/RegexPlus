package com.regexplus.automaton.base;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;
import com.regexplus.automaton.model.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StateAnd extends State {
    int /*counter, */counterVisitIndex;
    //boolean[] visited;
    public Map<Integer, boolean[]> visited = new HashMap<>();
    public StateAnd() {
        super ();
        this.counterVisitIndex = -1;
    }
    public StateType getType() {
        return StateType.AND;
    }
    public boolean visit(int visitIndex, IEdge edge) {
        if (this.counterVisitIndex != visitIndex) {
            this.counterVisitIndex = visitIndex;
            //this.counter = this.getInputEdges().size();
            this.visited.clear();
            //this.visited = new boolean[this.getInputEdges().size()];
            //Arrays.fill(visited, false);
        }
        for (Tag t: ((State) edge.getStart()).tags.values()) {
            if (t.finalState == this) {
                boolean v[] = null;
                if (!this.visited.containsKey(t.index)) {
                    v = new boolean[2];
                    Arrays.fill(v, false);
                    this.visited.put(t.index, v);
                } else {
                    v = this.visited.get(t.index);
                }

                v[t.type] = true;

                if (v[0] && v[1]) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        //if (!visited[this.getInputEdges().indexOf(edge)]

/*                &&
                (
                        this.matches().size() == 0 ||
                        ((State)edge.getFinish()).matches().get(0).start() ==
this.matches().get(0).start()
                )
          */
        //)
        //{
        //--this.counter;
        //}

        //visited[this.getInputEdges().indexOf(edge)] = true;

        //return this.counter == 0;
        return false;
    }
}
