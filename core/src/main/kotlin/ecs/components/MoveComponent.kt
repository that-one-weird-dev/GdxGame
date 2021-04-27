package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ecs.components.network.Synchronizable
import ecs.components.network.SynchronizedComponent
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable


class MoveComponent : SynchronizedComponent {
    val speed = Vector2()
    val prevPosition = Vector3()
    val interpolatedPosition = Vector3()

    override fun reset() {
        speed.set(0f, 0f)
    }

    override fun synchronize(data: Serializable) {
        if (data !is MoveComponentSync) return

        speed.set(data.speed)
    }

    override fun toSync(): Synchronizable = MoveComponentSync(speed)

    data class MoveComponentSync(
        val speed: Vector2
    ): Synchronizable {
        override fun getMapper(): ComponentMapper<out Component> = mapper

        override fun initialize(entity: EngineEntity) {
            entity.with<MoveComponent> {
                synchronize(this@MoveComponentSync)
            }
        }
    }

    companion object {
        val mapper = mapperFor<MoveComponent>()
    }
}

fun Entity.obtainMove() = this[MoveComponent.mapper]
    ?: throw KotlinNullPointerException("No MoveComponent given for entity $this")
