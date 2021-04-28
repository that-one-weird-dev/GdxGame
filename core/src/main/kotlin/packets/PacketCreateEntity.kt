package packets

import Game
import com.badlogic.gdx.utils.reflect.ClassReflection
import ecs.EntityComponent
import ecs.components.synchronization.pack
import ecs.components.synchronization.setField
import ktx.ashley.entity

class PacketCreateEntity(
    val components: Array<ComponentData>,
) : Packet {
    override fun execute(game: Game) {
        val engine = game.engine

        engine.entity {
            // Code from ktx.ashley.engines (using this because i can not use generics)
            components.forEach { comp ->
                val component = try {
                    engine.createComponent(comp.type)
                        ?: throw NullPointerException("The component of ${comp.type} type is null.")
                } catch (exception: Throwable) {
                    exception.printStackTrace()
                    return
                }.apply {
                    comp.fields.forEach { field ->
                        this.setField(field.key, field.value)
                    }
                }
                entity.add(component)
            }
        }
    }

    companion object {
        fun create(configure: MutableList<ComponentData>.() -> Unit): PacketCreateEntity {
            val conf = mutableListOf<ComponentData>()
            conf.configure()
            return PacketCreateEntity(conf.toTypedArray())
        }
    }
}

inline fun <reified T: EntityComponent> MutableList<ComponentData>.with(configure: T.() -> Unit = {}) {
    val comp = ClassReflection.newInstance(T::class.java)
    comp.configure()

    add(
        ComponentData(
            comp::class.java,
            comp.pack(),
        )
    )
}
