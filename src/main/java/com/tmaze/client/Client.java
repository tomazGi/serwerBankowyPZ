package com.tmaze.client;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost",12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Random random = new Random();
            int clientId = random.nextInt(1000);

            out.writeObject(clientId);
            out.flush();
            String status = (String) in.readObject();
            System.out.println("Status: " + status);
            if(status.equals("REFUSED")){
                socket.close();
                return;
            }

            String[] klasy = {"Klient","Bank","Konto","Koń"};

            for(String nazwa : klasy){
                out.writeObject(nazwa);
                out.flush();

                try{
                    List<?> lista = (List<?>) in.readObject();
                    System.out.println("\nKlient " + clientId +
                            " otrzymał obiekty klasy " + nazwa);
                    lista.stream()
                            .forEach(System.out::println);
                }
                catch(ClassCastException e){
                    System.out.println("Błąd rzutowania!");
                }

            }

            socket.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}