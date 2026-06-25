package com.tmaze;

import com.tmaze.model.Bank;
import com.tmaze.model.Klient;
import com.tmaze.model.Konto;
import com.tmaze.repo.DaneWejsciowe;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public class Main {
    
    public static int NUM_CLIENTS = 4;
    
    public static void main(String[] args) throws IOException {


        DaneWejsciowe dw = new DaneWejsciowe();


        AtomicReference<ServerSocket> serverSocket = new AtomicReference<>(new ServerSocket(12345));
        Thread[] clientThreads = new Thread[NUM_CLIENTS];

        for (int i = 0; i < NUM_CLIENTS; i++) {
            final int clientId = i + 1; // Identyfikator klienta (zaczynając od 1)

            // Tworzymy Runnable dla logiki klienta
            Runnable clientTask = () -> {


                while (true) try {
                    Socket socket = serverSocket.get().accept();
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os, true);
                    String fromClient;
                    while ((fromClient = br.readLine()) != null) {
                        System.out.println("From client: [" + fromClient + "]");
                        pw.println("OK");
                        fromClient = br.readLine();
                        pw.println(fromClient);
                        System.out.println(dw.allObj.containsKey(fromClient));
                        if (dw.allObj.keySet().stream().anyMatch(k -> k.contains("klient"))) {
                            for (Map.Entry<String, Object> entry : dw.allObj.entrySet()) {
                                if (entry.getKey().contains(fromClient)) {
                                    System.out.println(entry.getKey() + ": " + entry.getValue());
                                    pw.println(entry.getKey() + ": " + entry.getValue());
                                }
                            }
                        }
                    }
                    socket.close();
                } catch (Exception e) {
                    System.err.println("Server exception: " + e);
                }
            };
            clientThreads[i] = new Thread(clientTask, "KlientWątek-" + clientId);
            clientThreads[i].start();
        }
    }
}