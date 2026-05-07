package com.SIEM_TOOL.SIEM_TOOL;
import java.util.List;

public interface DetectionRule {
    List<Alert> detect(List<LogEntry> logs);
}