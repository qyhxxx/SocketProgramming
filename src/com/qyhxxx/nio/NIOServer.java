package com.qyhxxx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NIOServer extends Thread {
    private static final int port = 1024;
    private Selector selector;

    public NIOServer() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (selector.select() == 0)
                    continue;

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                        //写法一
                        StringBuilder stringBuilder = new StringBuilder();
                        int len;
                        while ((len = socketChannel.read(byteBuffer)) != -1) {
                            byteBuffer.flip();
                            stringBuilder.append(new String(byteBuffer.array(), 0, len));
                            byteBuffer.clear();
                        }
                        System.out.println(stringBuilder.toString());
                        socketChannel.close();

                        //写法二
//                        if (socketChannel.read(byteBuffer) != -1) {
//                            byteBuffer.flip();
//                            System.out.print(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
//                            byteBuffer.clear();
//                        } else {
//                            socketChannel.close();
//                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
