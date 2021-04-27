package packets

import Game
import ktx.ashley.get

//data class PacketUpdateComponent(
//    val entityId: String,
//    val component: Synchronizable,
//) : Packet {
//    override fun execute(game: Game) {
//        val entity = Game.entities[entityId]
//        val comp = entity?.get(component.getMapper()) ?: return
//
//        if (comp is SynchronizedComponent)
//            comp.synchronize(component)
//    }
//}