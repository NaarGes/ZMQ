package com.example.myapplication.pubsub;

import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import org.zeromq.SocketType;

import java.util.concurrent.TimeUnit;

public class Publisher {
    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.PUB);
            socket.bind("tcp://*:5559");
            System.out.println("starting loop");
            String message = "hello";
            while (!Thread.currentThread().isInterrupted()) {
                // Block until a message is received
                socket.send(message);

                // Print the message
                System.out.println(
                        "Sent: [" + message + "]"
                );
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
