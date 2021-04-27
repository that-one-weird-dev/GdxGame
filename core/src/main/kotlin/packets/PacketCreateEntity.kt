package packets

import Game
import ecs.GameComponent
import ecs.Synchronizable
import ecs.components.TransformComponent
import ecs.createEntityWithId
import ktx.ashley.EngineEntity
import ktx.ashley.with

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