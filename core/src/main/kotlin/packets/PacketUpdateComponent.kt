package packets

import Game
import ecs.components.network.Synchronizable
import ecs.components.network.SynchronizedComponent
import ktx.ashley.get

data class PacketUpdateComponent(
    val entityId: String,
    val component: Synchronizable,
) : Packet {
    override fun execute(game: Game) {
        val entity = Game.entities[entityId]
        val comp = entity?.get(component.getMapper()) ?: return

        if (comp is SynchronizedComponent)
            comp.synchronize(component)
    }
}