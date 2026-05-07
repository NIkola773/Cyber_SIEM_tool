package com.SIEM_TOOL.SIEM_TOOL;

public class LogEntry {
    private String ip;
    private String timestamp;
    private String method;
    private String endpoint;
    private int status;
    private String userAgent;

    public LogEntry(String ip, String timestamp, String method,
                    String endpoint, int status, String userAgent) {
        this.ip = ip;
        this.timestamp = timestamp;
        this.method = method;
        this.endpoint = endpoint;
        this.status = status;
        this.userAgent = userAgent;
    }

    public String getIp() { return ip; }
    public String getTimestamp() { return timestamp; }
    public String getMethod() { return method; }
    public String getEndpoint() { return endpoint; }
    public int getStatus() { return status; }
    public String getUserAgent() { return userAgent; }
}