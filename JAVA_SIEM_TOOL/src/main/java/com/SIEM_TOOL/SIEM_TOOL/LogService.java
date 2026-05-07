package com.SIEM_TOOL.SIEM_TOOL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LogService {

    public List<LogEntry> loadLogs(String filePath) throws IOException {

        List<LogEntry> logs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                LogParser.parse(line).ifPresent(logs::add);
            }
        }

        return logs;
    }
}