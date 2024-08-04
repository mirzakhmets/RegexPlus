package com.regexplus.parser;

import java.io.IOException;
import java.io.InputStream;

public class ParserInputStream implements IParserStream {
    int Current;
    InputStream is;

    public ParserInputStream(InputStream is) {
        this.is = is;
    }

    public void Read() {
        try {
            if (is.available() > 0) {
                this.Current = is.read();
            } else {
                this.Current = -1;
            }
        } catch (IOException e) {
            this.Current = -1;
            e.printStackTrace();
        }
    }

    public int getCurrent() {
        return this.Current;
    }
}
