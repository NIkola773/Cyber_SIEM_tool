package com.SIEM_TOOL.SIEM_TOOL;

public class Alert {
    private String type;
    private String message;
    private String ip;

    public Alert(String type, String message, String ip) {
        this.type = type;
        this.message = message;
        this.ip = ip;
    }

    public String getType() { return type; }
    public String getMessage() { return message; }
    public String getIp() { return ip; }
}