package com.SIEM_TOOL.SIEM_TOOL;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class PortScanner {
 public List<Ports>  performPortScan(String ipAddress) {
       
        int startPort = 1, endPort = 1024;
         List<Ports> listOpen = new ArrayList<>();
          List<Ports> listClosed = new ArrayList<>();

        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket()) {
              
                socket.connect(new InetSocketAddress(ipAddress, port), 250);

                listOpen.add(new Ports(port, STATE.OPEN));   
            } catch (IOException e) {
                listClosed.add(new Ports(port, STATE.CLOSED));
            }
          
        }
         return listOpen;
       
    }
    
}
