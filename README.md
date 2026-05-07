
# Cyber SIEM Tool

A lightweight Security Information and Event Management (SIEM) tool built with Spring Boot. Parses Apache-style access logs, detects common attack patterns, and includes a TCP port scanner — all accessible through a browser-based interface.

---

## Features

- **Brute Force Detection** — flags any IP with 5 or more HTTP 401 responses
- **Scanner / Reconnaissance Detection** — detects suspicious user-agents (`sqlmap`, `nikto`, `nmap`, `dirbuster`, `gobuster`, `wpscan`, etc.) and sensitive endpoint access (`/admin`, `/.env`, `/config`, `/phpmyadmin`, and more)
- **Log File Upload** — upload a `.log` file directly from the browser and get instant analysis results
- **TCP Port Scanner** — scan ports 1–1024 on any IP address and see which are open
- **Extensible Rule Engine** — new detection rules can be added by implementing the `DetectionRule` interface

---

## Tech Stack

- Java 17
- Spring Boot 4.x
- Maven
- Vanilla JS + Bootstrap 5 frontend (served as static content)

---

## Project Structure

```
src/
└── main/
    ├── java/com/SIEM_TOOL/SIEM_TOOL/
    │   ├── SiemToolApplication.java   # Entry point
    │   ├── SiemController.java        # REST endpoints
    │   ├── LogParser.java             # Regex-based Apache log parser
    │   ├── LogService.java            # File loading service
    │   ├── LogEntry.java              # Log record model
    │   ├── Alert.java                 # Alert model
    │   ├── DetectionRule.java         # Rule interface
    │   ├── RuleEngine.java            # Runs all rules against logs
    │   ├── BruteForceRule.java        # Detects brute force attempts
    │   ├── ScannerRule.java           # Detects scanners and recon tools
    │   ├── PortScanner.java           # TCP port scanner service
    │   └── Ports.java                 # Port state model
    └── resources/
        ├── static/index.html          # Browser UI
        └── application.properties
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+

### Run

```bash
git clone https://github.com/YOUR_USERNAME/siem-tool.git
cd siem-tool
./mvnw spring-boot:run
```

Then open your browser at `http://localhost:8080`.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/siem/upload` | Upload a `.log` file and get a list of alerts |
| `GET` | `/api/siem/portScan?data={ip}` | Scan ports 1–1024 on the given IP |
| `GET` | `/api/siem/analyze` | Analyze the bundled `access.log` sample |

### Example: Upload a log file

```bash
curl -X POST http://localhost:8080/api/siem/upload \
  -F "file=@access.log"
```

### Example response

```json
[
  {
    "type": "BRUTE_FORCE",
    "message": "Too many failed logins: 5",
    "ip": "192.168.1.20"
  },
  {
    "type": "SCANNER",
    "message": "Suspicious user-agent: sqlmap",
    "ip": "10.0.0.5"
  },
  {
    "type": "SCAN",
    "message": "Sensitive endpoint accessed: /admin",
    "ip": "172.16.0.3"
  }
]
```

---

## Log Format

The parser expects Apache Combined Log Format:

```
192.168.1.20 - - [03/May/2026:10:01:01] "POST /login HTTP/1.1" 401 512 "Mozilla/5.0"
```

Lines that don't match this format are silently skipped.

---

## Adding a New Detection Rule

1. Create a class that implements `DetectionRule`:

```java
public class MyRule implements DetectionRule {
    @Override
    public List<Alert> detect(List<LogEntry> logs) {
        // your logic here
        return alerts;
    }
}
```

2. Register it in `RuleEngine.java`:

```java
private final List<DetectionRule> rules = List.of(
    new BruteForceRule(),
    new ScannerRule(),
    new MyRule()   // add here
);
```

---

## Author

Nikola — Computer Science student, Singidunum University, Belgrade
