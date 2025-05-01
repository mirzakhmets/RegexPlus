package com.regexplus.automaton.base;

import com.regexplus.automaton.common.IEdge;
import com.regexplus.automaton.common.StateType;
import com.regexplus.automaton.model.State;
import com.regexplus.automaton.model.Tag;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StateAnd extends State {
    //boolean[] visited;
    public Map<Integer, boolean[]> visited = new HashMap<>();
    int /*counter, */counterVisitIndex;
    boolean passedComputed = false, passed = false;
    int visitedCounter = 0, currentVisitedCounter = 0;
    public boolean visitedAll[] = new boolean[2];

    public StateAnd() {
        super();
        this.counterVisitIndex = -1;
    }

    public StateAnd(int visitedCounter) {
        super();

        this.counterVisitIndex = -1;

        this.visitedCounter = visitedCounter;
    }

    public StateType getType() {
        return StateType.AND;
    }

    public boolean visit(int visitIndex, IEdge edge) {
        //if (passedComputed) {
        //    return passed;
        //}

        if (this.counterVisitIndex != visitIndex) {
            this.counterVisitIndex = visitIndex;
            //this.counter = this.getInputEdges().size();
            this.visited.clear();
            //this.visited = new boolean[this.getInputEdges().size()];
            //Arrays.fill(visited, false);
            this.currentVisitedCounter = this.visitedCounter;

            visitedAll[0] = visitedAll[1] = false;
        }

        visitedAll[this.getInputEdges().indexOf(edge)] = true;

        //return visitedAll[0] && visitedAll[1];

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

                passedComputed = true;

                passed = v[0] && v[1];

                return v[0] && v[1];
            }
        }

        --this.currentVisitedCounter;

        //if (!visited[this.getInputEdges().indexOf(edge)]
/* &&
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


        return this.currentVisitedCounter == 0;
    }
}
