package packets.client

import Game
import packets.ClientPacket

class CPacketRemoveEntity(
    val entityId: String,
) : ClientPacket {

    override fun execute(game: Game) {
        val entity = Game.entities[entityId]
        game.engine.removeEntity(entity)
    }
}