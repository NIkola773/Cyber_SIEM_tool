package com.SIEM_TOOL.SIEM_TOOL;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
 // this is a regex pattern for later comparing and finding malicious activity
    private static final String PATTERN =
            "^(\\S+) - - \\[(.*?)\\] \"(\\S+) (\\S+) .*\" (\\d{3}) .* \"(.*?)\"$";

    private static final Pattern pattern = Pattern.compile(PATTERN);

    public static Optional<LogEntry> parse(String line) {
        Matcher m = pattern.matcher(line);

        if (!m.matches()) return Optional.empty();

        return Optional.of(new LogEntry(
                m.group(1),
                m.group(2),
                m.group(3),
                m.group(4),
                Integer.parseInt(m.group(5)),
                m.group(6)
        ));
    }
}