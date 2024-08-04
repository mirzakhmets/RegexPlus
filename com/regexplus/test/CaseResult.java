package com.regexplus.test;

public class CaseResult {
    String string;
    int result;

    public CaseResult(String string, int result) {
        this.string = string;
        this.result = result;
    }

    public String getString() {
        return this.string;
    }

    public int getResult() {
        return this.result;
    }
}