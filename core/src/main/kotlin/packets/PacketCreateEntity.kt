package packets

import Game
import com.badlogic.gdx.utils.reflect.ClassReflection
import ecs.EntityComponent
import ecs.components.synchronization.pack
import ecs.components.synchronization.setField
import ktx.ashley.entity

class PacketCreateEntity(
    val components: Array<Component>,
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

    data class Component(val type: Class<out EntityComponent>, val fields: Map<String, Any?>)

    data class Configurator(
        val components: MutableList<Component> = mutableListOf()
    ) {
        inline fun <reified T : EntityComponent> with(configure: T.() -> Unit = {}) {
            val comp = ClassReflection.newInstance(T::class.java)
            comp.configure()

            components.add(
                Component(
                    comp::class.java,
                    comp.pack(),
                )
            )
        }

        fun toArray(): Array<Component> {
            return components.toTypedArray()
        }
    }

    companion object {
        fun create(configure: Configurator.() -> Unit): PacketCreateEntity {
            val conf = Configurator()
            conf.configure()
            return PacketCreateEntity(conf.toArray())
        }
    }
}