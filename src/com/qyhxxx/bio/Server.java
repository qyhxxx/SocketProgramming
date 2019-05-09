package com.qyhxxx.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private static final int port = 1024;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    //每个连接新开一个线程
                    new Thread(() -> {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            byte[] bytes = new byte[1024];
                            int len;
                            StringBuilder sb = new StringBuilder("server-thread" + Thread.currentThread().getId() + ":\n");
                            while ((len = inputStream.read(bytes)) != -1) {
                                sb.append(new String(bytes, 0, len));
                            }
                            System.out.println(sb);
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}