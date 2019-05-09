package com.qyhxxx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient extends Thread {
    private static final String host = "127.0.0.1";
    private static final int port = 1024;
    private SocketChannel socketChannel;

    public NIOClient() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            if (socketChannel.finishConnect()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                for (int i = 0; i < 5; i++) {
                    byteBuffer.clear();
                    byteBuffer.put(("client" + Thread.currentThread().getId() + "-" + i + ":hello world\n").getBytes());
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        socketChannel.write(byteBuffer);
                    }
                }
            }
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
