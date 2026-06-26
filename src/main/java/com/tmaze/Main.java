
package com.tmaze;
import com.tmaze.repo.DaneWejsciowe;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    public static final int PORT = 12345;
    public static final int MAX_CLIENTS = 4;

    private static final AtomicInteger activeClients = new AtomicInteger(0);

    public static void main(String[] args) {

        DaneWejsciowe dw = new DaneWejsciowe();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Serwer uruchomiony...");

            while (true) {

                Socket socket = serverSocket.accept();

                new Thread(() -> {

                    int clientId = -1;

                    try {

                        ObjectOutputStream out =
                                new ObjectOutputStream(socket.getOutputStream());

                        ObjectInputStream in =
                                new ObjectInputStream(socket.getInputStream());

                        clientId = (Integer) in.readObject();

                        if (activeClients.incrementAndGet() > MAX_CLIENTS) {

                            activeClients.decrementAndGet();

                            System.out.println("Klient "
                                    + clientId
                                    + " -> REFUSED");

                            out.writeObject("REFUSED");
                            out.flush();

                            socket.close();
                            return;
                        }

                        System.out.println("Klient "
                                + clientId
                                + " -> OK");

                        out.writeObject("OK");
                        out.flush();

                        Random random = new Random();

                        while (true) {

                            String className;

                            try {
                                className = (String) in.readObject();
                            } catch (Exception e) {
                                break;
                            }

                            Thread.sleep(random.nextInt(2000));

                            List<Object> lista =
                                    Collections.singletonList(dw.findByClassName(className));

                            System.out.println("--------------------------------");
                            System.out.println("Klient ID: " + clientId);
                            System.out.println("Żądana klasa: " + className);
                            System.out.println("Wysłano:");

                            lista.forEach(System.out::println);

                            System.out.println("--------------------------------");

                            out.writeObject(lista);
                            out.flush();

                        }

                    } catch (Exception e) {

                        System.out.println(e.getMessage());

                    } finally {

                        activeClients.decrementAndGet();

                        try {
                            socket.close();
                        } catch (Exception ignored) {
                        }

                    }

                }).start();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}