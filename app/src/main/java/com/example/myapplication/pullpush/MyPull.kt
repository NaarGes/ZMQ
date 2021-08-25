import org.zeromq.ZMQ

/**
 * To create a connection between two nodes, you use zmq_bind() in one node and zmq_connect() in the other.
 As a general rule of thumb, the node that does zmq_bind() is a “server”, sitting on a well-known network address,
 and the node which does zmq_connect() is a “client”, with unknown or arbitrary network addresses.
 Thus we say that we “bind a socket to an endpoint” and “connect a socket to an endpoint”,
 the endpoint being that well-known network address.
 */

// client side of socket (consumer of data)
fun main() {
    // infinite loop where we'll be consistently pulling data from a particular socket
    val context = ZMQ.context(1)
    val socket = context.socket(ZMQ.PULL)

    socket.bind("tcp://localhost:5897")

    while (true) {
        val rawRequest = socket.recv()
        val cleanRequest = String(rawRequest)
        println("pulled some data: $cleanRequest")
    }
}