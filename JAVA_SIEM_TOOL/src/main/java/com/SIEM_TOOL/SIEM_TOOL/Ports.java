package com.SIEM_TOOL.SIEM_TOOL;
enum STATE {
    OPEN,
    CLOSED

}
public class Ports {
     
    int port;
      
    STATE state;
  
    public Ports(int port, STATE state) {
        this.port = port;
        this.state = state;
    }

    public int getPort() {
        return port;
    }
    
    public STATE getState() {
        return state;
    }
    // @Override
    // public String toString() {
    //     return "Ports [portNumber=" + port + ", state=" + state + "]";
    // }
    
}
