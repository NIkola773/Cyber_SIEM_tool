package com.SIEM_TOOL.SIEM_TOOL;


enum STATE {
    OPEN,
    CLOSED

}
public class Ports {

    private final int port;
    private final STATE state;
    private final String banner;

   
    public Ports(int port, STATE state, String banner) {
        this.port = port;
        this.state = state;
        this.banner = banner;
    }

 

    public int getPort() { return port; }
    public STATE getState() { return state; }
    public String getBanner() { return banner; }
}