package com.example.myapplication.repreq
import org.zeromq.ZMQ


fun main() {
    val context = ZMQ.context(1)

    val socket = context.socket(ZMQ.REQ)
    println("Connecting to hello world server...")

    // connect to same port as server does
    socket.connect("tcp://localhost:5879")

    for (i in 1..10) {
        val plainRequest = "Hello "

        // convert String to byteArray and add 0 delimiter for ZeroMQ before sending it to the socket
        val byteRequest = plainRequest.toByteArray()
//        byteRequest[byteRequest.size - 1] = 0
        println("sending request $i $plainRequest")

        socket.send(byteRequest, 0)

        // once it's sent, we'll listen for a reply, do this immediately after sending request
        // convert ByteArray to String and print it
        val byteReply = socket.recv(0)
        val plainReply = String(byteReply)
        println("Received replay $plainReply")
    }
}