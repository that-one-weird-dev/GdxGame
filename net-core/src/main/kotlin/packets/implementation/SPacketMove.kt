package packets.implementation

import packets.reflection.Implement
import packets.server.SPacketMove

@Implement
fun SPacketMove.execute() {
    println("Executing SPacketMove")
}