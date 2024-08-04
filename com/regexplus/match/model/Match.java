package com.regexplus.match.model;

import com.regexplus.automaton.common.IStream;
import com.regexplus.match.common.IMatch;

import java.util.List;

public class Match implements IMatch {
    public static final int NO_MATCH = -1;
    IStream stream;
    int start, end;

    public Match(IStream stream, int start) {
        this.stream = stream;
        this.start = start;
        this.end = NO_MATCH;
    }

    public static boolean isBetter(List<IMatch> a, List<IMatch> b) {
        //return true;
        if (b.size() == 0) {
            return true;
        }
        int min = Math.min(a.size(), b.size());
        for (int i = 0; i < min; ++i) {
            Match matcha = (Match) a.get(i);
            Match matchb = (Match) b.get(i);
            if (matcha.isBetter(matchb)) {
                return true;
            }
        }
        return false;
    }

    public IStream stream() {
        return this.stream;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean matches() {
        return this.end != NO_MATCH;
    }

    public int length() {
        return this.end == NO_MATCH ? NO_MATCH : this.end - this.start;
    }

    public Match copy() {
        Match result = new Match(this.stream, this.start);
        result.setEnd(this.end);
        return result;
    }

    public boolean isBetter(Match match) {
 /*
 if (this.start != -1 && match.start == -1) {
 return true;
 }
 if (this.end != -1 && match.end == -1) {
 return true;
 }*/
        if (this.end != -1 && match.end == -1) {
            return true;
        }
        if (this.start < match.start) {
            return true;
        }
        return this.end > match.end;
    }
}