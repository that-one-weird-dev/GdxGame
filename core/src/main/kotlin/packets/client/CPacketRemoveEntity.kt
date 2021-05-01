package packets.client

import Game
import packets.ClientPacket

class CPacketRemoveEntity(
    val entityId: String,
) : ClientPacket {

    override fun execute() {
        val entity = Game.entities[entityId]
        Game.engine.removeEntity(entity)
    }
}