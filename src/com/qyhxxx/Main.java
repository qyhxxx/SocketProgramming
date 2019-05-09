package com.qyhxxx;

import com.qyhxxx.nio.NIOClient;
import com.qyhxxx.nio.NIOServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
//        Thread server = new Server();
//        server.start();
//        ExecutorService threadsPool = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            Thread client = new Client();
//            threadsPool.submit(client);
//        }

        Thread nioServer = new NIOServer();
        nioServer.start();
        ExecutorService threadsPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Thread nioClient = new NIOClient();
            threadsPool.submit(nioClient);
        }
    }
}