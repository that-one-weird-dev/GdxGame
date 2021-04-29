import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.utils.ObjectMap
import ecs.components.*
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.collections.set
import ktx.log.debug
import ktx.log.logger
import packets.PacketCreateEntity
import java.util.*


private const val PORT = 8000

private val LOG = logger<Application>()

class Application : ApplicationListener {

    val entities: ObjectMap<String, Entity> = ObjectMap()

    val server = Server(PORT, this)
    val engine by lazy {
        PooledEngine().apply {
        }
    }

    override fun create() {
        engine.addEntityListener(object : EntityListener {
            override fun entityAdded(entity: Entity) {
                val id = entity[IDComponent.mapper]
                if (id == null) {
                    LOG.debug { "Added an entity without an ID, deleting it" }
                    engine.removeEntity(entity)
                    return
                }
                entities[id.id] = entity

                server.send(PacketCreateEntity.fromEntity(entity))
            }

            override fun entityRemoved(entity: Entity) {
                val id = entity[IDComponent.mapper]
                    ?: return LOG.debug { "Tried removing an entity without and ID, this should be impossible" }

                entities.remove(id.id)
            }
        })

        server.start()

        engine.entity {
            with<IDComponent> {
                id = UUID.randomUUID().toString()
            }
            with<TransformComponent> {
                setInitialPosition(8f, 4.5f, 0f)
                size.set(2.5f, 2.5f)
            }
            with<AnimationComponent>()
            with<GraphicComponent>()
            with<PlayerComponent>()
            with<MoveComponent>()
            with<FlipComponent>()
        }
    }

    override fun dispose() {}

    // Useless methods (because this is headless)
    override fun resize(width: Int, height: Int) {}
    override fun render() {}
    override fun pause() {}
    override fun resume() {}
}