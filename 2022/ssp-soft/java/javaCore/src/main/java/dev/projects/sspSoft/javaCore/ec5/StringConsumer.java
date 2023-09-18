package dev.projects.sspSoft.javaCore.ec5;

import java.util.StringJoiner;

public class StringConsumer {
    private final StringJoiner joiner = new StringJoiner("","","");

    public void join(String value) {
        joiner.add(value);
    }

    @Override
    public String toString() {
        return joiner.toString();
    }
}
