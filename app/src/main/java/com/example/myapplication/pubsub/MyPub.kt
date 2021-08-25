package com.example.myapplication.pubsub
import org.zeromq.ZMQ


// server side of socket (provider of data)
fun main() {
    val context = ZMQ.context(0)
    val socket = context.socket(ZMQ.PUB)

    // connect to address where puller bound itself
    println("Connecting to subscribed clients...")
    socket.connect("tcp://localhost:5897")

    for (i in 1..20) {
        Thread.sleep(100)
        val plainRequest = "Hello "
        val byteRequest = plainRequest.toByteArray()
        println("publishing data $i $plainRequest")

        socket.send(byteRequest, 0)
    }
}