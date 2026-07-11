package com.SIEM_TOOL.SIEM_TOOL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;


@Service
public class PortScanner {

    public List<Ports> performPortScan(String ipAddress) {

        ExecutorService ex = Executors.newFixedThreadPool(100);
        int startPort = 1, endPort = 1024;

        // Collections.synchronizedList  - this allows all 100 threads to access the data in the list
        
       // basic arraylist can lose elements and throw errors
      
        List<Ports> listOpen = Collections.synchronizedList(new ArrayList<>());
        List<Ports> listClosed = Collections.synchronizedList(new ArrayList<>());

        for (int port = startPort; port <= endPort; port++) {
            int currentPort = port;

            ex.submit(() -> {
                try (Socket socket = new Socket()) {
                    // this connects to the entered ip address (timeout 250 ms) the for loop allows iteration through all 1024 ports
                    socket.connect(new InetSocketAddress(ipAddress, currentPort), 250);

                  
                    String banner = null;

                    try {
                        socket.setSoTimeout(300);

                     // BufferedReader allows to read data that InputStreamReader translates to readable text. (originaly from bytes)
                        BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                        );
                           // reads data form the port and adds it into banner (banner shows more data of the service)
                        String line = reader.readLine();
                        if (line != null && !line.isBlank()) {
                            banner = line.trim();
                        }
                    } catch (IOException timeoutOrNoData) {
                        
                    }
                    
                  //adds the data to the list of open ports
                    listOpen.add(new Ports(currentPort, STATE.OPEN, banner));

                } catch (IOException e) {
                     //adds the data to the list of closed ports
                    listClosed.add(new Ports(currentPort, STATE.CLOSED, null));
                }
            });
        }

        ex.shutdown();
        try {
           // we wait for the all threads to finish max 30 seconds and then  return listOpen 
            ex.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // this part combines two lists listOpen and Closed
 List<Ports> allResults = new ArrayList<>();
        allResults.addAll(listOpen);
        allResults.addAll(listClosed);
 
    
        allResults.sort((a, b) -> Integer.compare(a.getPort(), b.getPort()));
 
        return allResults;
    }
}