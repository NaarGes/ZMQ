package com.example.myapplication.pubsub
import org.zeromq.ZMQ


// client side of socket (consumer of data)
fun main() {
    // infinite loop where we'll be consistently pulling data from a particular socket
    val context = ZMQ.context(1)
    val socket = context.socket(ZMQ.SUB)

    socket.bind("tcp://localhost:5897")

    while (true) {
        val rawRequest = socket.recv()
        val cleanRequest = String(rawRequest)
        println("subscribed to some data: $cleanRequest")
    }
}