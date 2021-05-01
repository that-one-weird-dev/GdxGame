package packets.client

import Game
import com.badlogic.ashley.core.Component
import ecs.components.synchronization.callMethod
import ecs.components.synchronization.getComponentMapper
import ktx.ashley.get
import packets.Packet

class PacketCallMethod(
    private val entityId: String,
    private val componentType: Class<out Component>,
    private val methodName: String,
    private val args: Array<Any?> = arrayOf(),
) : Packet {
    override fun execute(game: Game) {
        val entity = Game.entities[entityId]
        val comp = entity?.get(componentType.getComponentMapper()) ?: return

        comp.callMethod(methodName, *args)
    }
}