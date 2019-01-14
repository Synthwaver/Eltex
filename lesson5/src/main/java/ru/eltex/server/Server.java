package ru.eltex.server;

import ru.eltex.phonebook.PhoneBook;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server extends Thread {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(80);
            System.out.println("Server started");
            while (true) {
                new Server(server.accept());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    private Server(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
        setDaemon(true);
        start();
    }

    public void run() {
        try {
            String inputHeader = readInputHeaders();
            String filename = inputHeader.split("/")[1].replace("HTTP", "").trim();
            if (filename.length() <= 1) {
                filename = "index.html";
            }
            System.out.println("File requested: " + filename);
            byte[] byteArray;
            if (filename.equals("phonebook")) {
                byteArray = new PhoneBook("phonebook.csv").getUsersHtmlTable();
            } else {
                byteArray = Files.readAllBytes(Paths.get(filename));
            }
            writeResponse(byteArray);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        try {
            socket.close();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        System.out.println("Client processing finished");
    }

    private String readInputHeaders() throws IOException {
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder strBuilder = new StringBuilder();
        while (true) {
            String line = bufReader.readLine();
            if (line == null || line.trim().length() == 0) {
                break;
            }
            strBuilder.append(line);
        }
        return strBuilder.toString();
    }

    private void writeResponse(byte[] byteArray) throws IOException {
        String header = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n" +
                "Content-Length: " + byteArray.length +
                "\r\n\r\n";

        out.write(header.getBytes());
        out.write(byteArray);
        out.flush();
    }
}
