package com.example.myapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.zeromq.ZMQ

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.server).setOnClickListener {
            AsyncTask.execute {
                val context = ZMQ.context(1)
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
                socket.close()
            }
        }

        findViewById<Button>(R.id.client).setOnClickListener {
            // infinite loop where we'll be consistently pulling data from a particular socket
            AsyncTask.execute {
                val context = ZMQ.context(1)
                val socket = context.socket(ZMQ.SUB)
                print(">>>>>> $socket")

                socket.bind("tcp://localhost:5897")
                socket.subscribe("");
                while (true) {
                    val rawRequest = socket.recv()
                    val cleanRequest = String(rawRequest)
                    println("subscribed to some data: $cleanRequest")
                }
            }
        }
    }
}
