package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.zeromq.SocketType
import org.zeromq.ZMQ
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.server).setOnClickListener {
            val serverExecutor = Executors.newSingleThreadExecutor()
            serverExecutor.execute {
                val context = ZMQ.context(1)
                val socket = context.socket(SocketType.PUB)

                // connect to address where puller bound itself
                println("Connecting to subscribed clients...")
                socket.bind("tcp://127.0.0.1:5559")

                for (i in 1..20) {
                    Thread.sleep(100)
                    val plainRequest = "Hello "
                    val sent = socket.send(plainRequest, 0)
                    println("publishing data $i $plainRequest, published status: $sent")

                }
                socket.close()
            }
        }

        findViewById<Button>(R.id.client).setOnClickListener {
            // infinite loop where we'll be consistently pulling data from a particular socket
            val clientExecutor = Executors.newSingleThreadExecutor()
            clientExecutor.execute {
                val context = ZMQ.context(1)
                val socket = context.socket(SocketType.SUB)

                socket.connect("tcp://127.0.0.1:5559")
                socket.subscribe("")
                while (true) {
                    val rawRequest = socket.recvStr(0)
                    println("subscribed to some data: $rawRequest")
                }
            }
        }
    }
}
