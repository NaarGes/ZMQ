package com.example.myapplication.pubsub;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import org.zeromq.SocketType;

public class Subscriber {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.SUB);
            socket.connect("tcp://127.0.0.1:5559");
            socket.subscribe("");
            System.out.println("starting loop");

            while (!Thread.currentThread().isInterrupted()) {
                // Block until a message is received
                String message = socket.recvStr(0);

                // Print the message
                System.out.println(
                        "Received: [" + message + "]"
                );
            }
        }
    }
}
