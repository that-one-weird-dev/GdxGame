import kotlinx.coroutines.runBlocking
import net.joinu.rudp.RUDPSocket
import java.net.InetSocketAddress

class GameServer(port: Int) {
    private val socket = RUDPSocket().apply {
        bind(InetSocketAddress(port))
    }
    private val clients = setOf<InetSocketAddress>()

    private fun run(): Nothing {
        runBlocking {
            while (true) {
                val message = socket.receive()
            }
        }
    }
}