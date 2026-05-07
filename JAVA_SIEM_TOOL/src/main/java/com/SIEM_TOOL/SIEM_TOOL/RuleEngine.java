package com.SIEM_TOOL.SIEM_TOOL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RuleEngine {
   // List of rules for finding malicious activity 
    private final List<DetectionRule> rules = List.of(
            new BruteForceRule(),
            new ScannerRule()
    );

    public List<Alert> analyze(List<LogEntry> logs) {
        List<Alert> alerts = new ArrayList<>();

        for (DetectionRule rule : rules) {
            alerts.addAll(rule.detect(logs));
        }

        return alerts;
    }
    
}