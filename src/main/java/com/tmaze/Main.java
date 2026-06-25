package com.tmaze;

import com.tmaze.model.Bank;
import com.tmaze.model.Klient;
import com.tmaze.model.Konto;

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

        Klient klient1 = new Klient("Jan Heweliusz", 1);
        Klient klient2 = new Klient("Edmund Hubble",2);
        Klient klient3 = new Klient("Albert Einstein",3);
        Klient klient4 = new Klient("Mikołaj Kopernik",4);

        Bank bank1 = new Bank("Erste", new BigInteger("123456789"));
        Bank bank2 = new Bank("Erste", new BigInteger("777777777"));
        Bank bank3 = new Bank("Erste", new BigInteger("888888888"));
        Bank bank4 = new Bank("Erste", new BigInteger("100000000"));

        Konto konto1 = new Konto(BigDecimal.valueOf(120000), klient1.getWlasciciel());
        Konto konto2= new Konto(BigDecimal.valueOf(6660000), klient2.getWlasciciel());
        Konto konto3 = new Konto(BigDecimal.valueOf(206265), klient3.getWlasciciel());
        Konto konto4 = new Konto(BigDecimal.valueOf(0.12), klient4.getWlasciciel());

        Map<String, Object> allObj = new HashMap<>();
        allObj.put("klient_1", klient1);
        allObj.put("klient_2", klient2);
        allObj.put("klient_3", klient3);
        allObj.put("klient_4", klient4);
        allObj.put("bank_1", bank1);
        allObj.put("bank_2", bank2);
        allObj.put("bank_3", bank3);
        allObj.put("bank_4", bank4);
        allObj.put("konto_1", konto1);
        allObj.put("konto_2", konto2);
        allObj.put("konto_3", konto3);
        allObj.put("konto_4", konto4);



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
                        pw.println("Echo: OK");
                        fromClient = br.readLine();
                        pw.println(fromClient);
                        System.out.println(allObj.containsKey(fromClient));
                        if (allObj.keySet().stream().anyMatch(k -> k.contains("klient"))) {
                            for (Map.Entry<String, Object> entry : allObj.entrySet()) {
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