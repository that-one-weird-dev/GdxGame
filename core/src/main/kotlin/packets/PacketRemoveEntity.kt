package packets

import Game

class PacketRemoveEntity(
    val entityId: String,
) : Packet {

    override fun execute(game: Game) {
        val entity = Game.entities[entityId]
        game.engine.removeEntity(entity)
    }
}