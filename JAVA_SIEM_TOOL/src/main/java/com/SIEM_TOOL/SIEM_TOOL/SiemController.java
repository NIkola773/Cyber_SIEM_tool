package com.SIEM_TOOL.SIEM_TOOL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/siem")
public class SiemController {
    @Autowired
    private PortScanner ps;

    private final LogService logService;
    
    private final RuleEngine ruleEngine;

    public SiemController(LogService logService, RuleEngine ruleEngine) {
        this.logService = logService;
        this.ruleEngine = ruleEngine;
    }
    @GetMapping("/portScan")
    public List<Ports> portScan(@RequestParam String data) {
         return ps.performPortScan(data);
    }
 // this can be used if you want to import the file without html form 
    @GetMapping("/analyze")
    public List<Alert> analyze() throws Exception {

        List<LogEntry> logs = logService.loadLogs("access.log");

        return ruleEngine.analyze(logs);
    }

    @PostMapping("/upload")
public List<Alert> upload(@RequestParam("file") MultipartFile file) throws Exception {

    if (file.isEmpty()) {
        throw new RuntimeException("Empty file");
    }
    // this checks if the file is a log file, if it's not throws an exception
    if (file.getOriginalFilename() == null || 
    !file.getOriginalFilename().toLowerCase().endsWith(".log")) {
    throw new RuntimeException("Only .log files allowed");
}

    List<LogEntry> logs = new ArrayList<>();
 // this part of the code, reads the content of the log file and call's the analyze function
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(file.getInputStream()))) {

        String line;
        while ((line = reader.readLine()) != null) {
            LogParser.parse(line).ifPresent(logs::add);
        }
    }

    return ruleEngine.analyze(logs);
}
}