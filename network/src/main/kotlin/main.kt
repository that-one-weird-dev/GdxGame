import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.joinu.rudp.RUDPSocket
import net.joinu.rudp.runSuspending
import net.joinu.rudp.send
import java.net.InetSocketAddress

fun main() {
    val addr1 = InetSocketAddress(1337)
    val addr2 = InetSocketAddress(1338)

    val s1 = RUDPSocket().apply { bind(addr1) }
    val s2 = RUDPSocket().apply { bind(addr2) }

    runBlocking {
        launch { s1.runSuspending() }
        launch { s2.runSuspending() }

        s1.send(ByteArray(10), addr2)

        val msg = s2.receive()
    }

    s1.close()
    s2.close()
}