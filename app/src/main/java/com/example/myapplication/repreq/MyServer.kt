import org.zeromq.SocketType
import org.zeromq.ZMQ

/**
 * source code from:
 * https://medium.com/@thealmikey/zeromq-with-kotlin-part-1-simple-server-client-example-cfea2938e2c2
 */

fun main() {
    val context = ZMQ.context(1)
//    var oldSocket = context.socket(ZMQ.REP);
    val socket = context.socket(SocketType.REP)
    println("Starting the server...")

    // bind the socket to a free port
    socket.bind("tcp://*:5897")

    // to run server non-stop, create an infinite loop, inside loop is where server will receive request
    while (true) {
        val rawRequest = socket.recv(0)

        // in ZMQ requests and replies are sent as length-specific binary data
        // so [rawRequest] type is Array[Byte] which can easily converted back into String
        val cleanRequest = String(rawRequest)
        println("Request received : $cleanRequest")

        // store reply to a variable and convert it to a ByteArray that can be sent over the wire
        val plainReply = "World! "
        val rawReply = plainReply.toByteArray();

        // set the last byte of rawReply to 0, this is used as a delimiter between messages.
        // this is used by zeromq to differentiate where on message ends and the other begins
        // EDIT:  In Kotlin, the messages aren’t zero-terminated but are simply length-based.
        // Converting a string to a bytearray and then directly sending it to the wire
        // will work without doing anything else
//        rawReply[rawReply.size - 1] = 0;

        // now we are ready to send the reply back to the client
        socket.send(rawReply, 0);

    }
}