package packets

import Game
import ecs.components.network.Synchronizable
import ecs.createEntityWithId

class PacketCreateEntity(
    val components: Array<Synchronizable>
) : Packet {
    override fun execute(game: Game) {
        val s: Synchronizable = components[0]
        game.engine.createEntityWithId {
            components.forEach {
                it.initialize(this)
            }
        }
    }
}