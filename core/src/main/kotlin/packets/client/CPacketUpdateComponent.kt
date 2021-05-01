package packets.client

import Game
import ecs.components.synchronization.getComponentMapper
import ecs.components.synchronization.setField
import ktx.ashley.get
import packets.ClientPacket
import packets.ComponentData

data class CPacketUpdateComponent(
    val entityId: String,
    val component: ComponentData,
) : ClientPacket {
    override fun execute() {
        val entity = Game.entities[entityId]
        val comp = entity?.get(component.type.getComponentMapper()) ?: return

        component.fields.forEach {
            comp.setField(it.key, it.value)
        }
    }
}