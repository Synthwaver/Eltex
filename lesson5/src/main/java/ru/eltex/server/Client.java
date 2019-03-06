package ru.eltex.server;

import ru.eltex.phonebook.PhoneBook;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Runnable {
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private final PhoneBook phoneBook;

    private static final String PHONEBOOK_PAGE = "phonebook";

    private static final Pattern pagePattern = Pattern.compile("GET /(.*?) .*");

    public Client(Socket socket, PhoneBook phonebook) throws IOException {
        this.socket = socket;
        in = this.socket.getInputStream();
        out = this.socket.getOutputStream();
        this.phoneBook = phonebook;

        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        try {
            String header = readHeader();
            String page = getRequestedPage(header);
            if (page == null) {
                throw new Exception("Couldn't resolve requested page in the header\n" + header);
            }
            if (page.isEmpty()) {
                page = "index.html";
            }
            sendResponse(getResponseContent(page));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: '" + e.getMessage() + "'");
        } catch (Throwable t) {
            System.err.println("Failed to process client's request:");
            System.err.println(t.getMessage());
            t.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket:");
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String readHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty())
                break;
            sb.append(line).append("\r\n");
        }
        return sb.toString();
    }

    private static String getRequestedPage(String header) {
        if (!header.startsWith("GET")) {
            throw new UnsupportedOperationException("Given request is not a GET request");
        }
        Matcher matcher = pagePattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private byte[] getResponseContent(String page) throws IOException {
        if (page.equals(PHONEBOOK_PAGE)) {
            return phoneBook.getUsersHtmlTable().getBytes();
        }

        try (RandomAccessFile file = new RandomAccessFile(page, "r")) {
            byte[] content = new byte[(int)file.length()];
            file.readFully(content);
            return content;
        }
    }

    private void sendResponse(byte[] content) throws IOException {
        final String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Content-Length: " + content.length + "\r\n\r\n";
        out.write(response.getBytes());
        out.write(content);
        out.flush();
    }
}