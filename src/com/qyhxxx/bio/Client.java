package com.qyhxxx.bio;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private static final String host = "127.0.0.1";
    private static final int port = 1024;

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            OutputStream out = socket.getOutputStream();
            for (int i = 0; i < 5; i++) {
                out.write(("client" + Thread.currentThread().getId() + "-" + i + ":hello world\n").getBytes());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}