package ru.eltex.server;

import ru.eltex.phonebook.PhoneBook;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable, Closeable {
    private final ServerSocket serverSocket;
    private final PhoneBook phonebook;
    private final Thread acceptanceThread;

    public Server(int port, PhoneBook phonebook) throws IOException {
        this.phonebook = phonebook;

        serverSocket = new ServerSocket(port);
        acceptanceThread = new Thread(this);
        acceptanceThread.setDaemon(true);
        acceptanceThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Client(clientSocket, phonebook);
            } catch (IOException e) {
                System.err.println("Failed to serve the client:");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws IOException {
        acceptanceThread.interrupt();
        serverSocket.close();
    }
}
