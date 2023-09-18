package dev.projects.profsouz.opcuaclient.utils;

import java.util.StringJoiner;
import java.util.regex.Pattern;

public class OpcUaWsStompCheckUtil {
    private final static String wsHostPatternMatch = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}|localhost$", wsPortPatternMathc = "\\d{2,5}";

    private static Pattern wsHostPattern = Pattern.compile(wsHostPatternMatch), wsPortPattern = Pattern.compile(wsPortPatternMathc);


    public static void checkWsHostAndPort(String wsHost, String wsPort) throws Exception {
        if (wsHost == null || wsHost.isEmpty() || wsPort == null || wsPort.isEmpty()) {
            throw new IllegalArgumentException("Invalid wsHost or wsPort.");
        }

        if (!wsHostPattern.matcher(wsHost).matches()) {
            throw new IllegalArgumentException("Invalid wsHost.");
        }

        if (!wsPortPattern.matcher(wsPort).matches()) {
            throw new IllegalArgumentException("Invalid wsPort.");
        }
    }

    public static String createWsURLFromHostAndPort(String wsHost, String wsPort) {
        return (new StringJoiner("","ws://", ""))
                .add(wsHost)
                .add(":")
                .add(wsPort)
                .add("/")
                .toString();
    }
}
