package com.SIEM_TOOL.SIEM_TOOL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BruteForceRule implements DetectionRule {

    @Override
    public List<Alert> detect(List<LogEntry> logs) {
        // this part checks for failed log in attempts, if there is more then 5 or if it -
        // equals five failed log in attempts, it add an alert named 
        Map<String, Long> failed = logs.stream()
                .filter(l -> l.getStatus() == 401)
                .collect(Collectors.groupingBy(LogEntry::getIp, Collectors.counting()));

        List<Alert> alerts = new ArrayList<>();

        for (var entry : failed.entrySet()) {
            if (entry.getValue() >= 5) {
                alerts.add(new Alert(
                        "BRUTE_FORCE",
                        "Too many failed logins: " + entry.getValue(),
                        entry.getKey()
                ));
            }
        }

        return alerts;
    }
}