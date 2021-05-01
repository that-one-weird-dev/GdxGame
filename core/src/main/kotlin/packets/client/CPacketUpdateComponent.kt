package packets.client

import Game
import ecs.components.synchronization.getComponentMapper
import ecs.components.synchronization.setField
import ktx.ashley.get
import packets.ComponentData
import packets.ClientPacket

data class CPacketUpdateComponent(
    val entityId: String,
    val component: ComponentData,
) : ClientPacket {
    override fun execute(game: Game) {
        val entity = Game.entities[entityId]
        val comp = entity?.get(component.type.getComponentMapper()) ?: return

        component.fields.forEach {
            comp.setField(it.key, it.value)
        }
    }
}