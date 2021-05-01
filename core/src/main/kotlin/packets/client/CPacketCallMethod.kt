package packets.client

import Game
import com.badlogic.ashley.core.Component
import ecs.components.synchronization.callMethod
import ecs.components.synchronization.getComponentMapper
import ktx.ashley.get
import packets.ClientPacket

class CPacketCallMethod(
    private val entityId: String,
    private val componentType: Class<out Component>,
    private val methodName: String,
    private val args: Array<Any?> = arrayOf(),
) : ClientPacket {
    override fun execute() {
        val entity = Game.entities[entityId]
        val comp = entity?.get(componentType.getComponentMapper()) ?: return

        comp.callMethod(methodName, *args)
    }
}