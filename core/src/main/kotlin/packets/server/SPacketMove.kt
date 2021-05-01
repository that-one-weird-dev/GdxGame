package packets.server

import packets.ServerPacket

class SPacketMove(
    val x: Byte,
    val y: Byte,
) : ServerPacket