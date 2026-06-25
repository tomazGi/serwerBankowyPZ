package com.tmaze.client;
import java.io.*;
import java.net.*;


public class Client {
    public static void main(String args[]) {
        try {
            for (int i = 0; i < 10; i++) {
                Socket socket = new Socket("localhost", 12345);
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println(i);
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String fromServer = br.readLine();
                System.out.println("From server: " + fromServer);
                pw.println("klient");
                while ((fromServer = br.readLine()) != null) {


                    System.out.println(fromServer);
                }
                socket.close();
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }
    }
}

