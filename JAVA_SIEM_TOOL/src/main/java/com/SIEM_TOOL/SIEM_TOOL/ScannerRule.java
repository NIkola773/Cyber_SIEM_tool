package com.SIEM_TOOL.SIEM_TOOL;

import java.util.ArrayList;
import java.util.List;

public class ScannerRule implements DetectionRule {
  // a basic list of suspicious tools
    private static final List<String> SUSPICIOUS_AGENTS = List.of(
            "sqlmap",
            "nikto",
            "nmap",
            "dirbuster",
            "gobuster",
            "wpscan",
            "curl",
            "python-requests"
    );

    private static final List<String> SUSPICIOUS_ENDPOINTS = List.of(
            "/admin",
            "/phpmyadmin",
            "/wp-login",
            "/config",
            "/backup",
            "/.env"
    );

    @Override
    public List<Alert> detect(List<LogEntry> logs) {

        List<Alert> alerts = new ArrayList<>();
//  Iterates through every LogEntry object and checks for -
//  suscpicious patterns listed above, and adds the suspicious activity to alerts list
        for (LogEntry log : logs) {

            String ua = log.getUserAgent().toLowerCase();
            String endpoint = log.getEndpoint().toLowerCase();

            
            for (String agent : SUSPICIOUS_AGENTS) {
                if (ua.contains(agent)) {
                    alerts.add(new Alert(
                            "SCANNER",
                            "Suspicious user-agent: " + agent,
                            log.getIp()
                    ));
                }
            }

          
            for (String ep : SUSPICIOUS_ENDPOINTS) {
                if (endpoint.contains(ep)) {
                    alerts.add(new Alert(
                            "SCAN",
                            "Sensitive endpoint accessed: " + ep,
                            log.getIp()
                    ));
                }
            }
        }

        return alerts;
    }
}