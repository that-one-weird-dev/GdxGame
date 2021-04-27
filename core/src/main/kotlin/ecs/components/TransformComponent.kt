package ecs.components

import Game
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

class TransformComponent : SynchronizedComponent, Comparable<TransformComponent> {
    val position = Vector3()
    val globalPosition = Vector3()

    val size = Vector2(1f, 1f)
    var rotationDeg = 0f

    var parent: Entity? = null

    override fun reset() {
        position.set(Vector3.Zero)
        size.set(Vector2.Zero)
        parent = null
        rotationDeg = 0f
    }

    fun setInitialPosition(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
    }

    override fun compareTo(other: TransformComponent): Int {
        val zDiff = other.position.z.compareTo(position.z)
        return if (zDiff == 0) other.position.y.compareTo(position.y) else zDiff
    }

    override fun synchronize(data: Serializable) {
        if (data !is TransformComponentSync) return

        if (data.position != null)
            this.position.set(data.position)
        if (data.size != null)
            this.size.set(data.size)
        if (data.rotation != null)
            this.rotationDeg = data.rotation
        if (data.parent != null)
            this.parent = Game.entities[data.parent]
    }

    override fun toSync(): Synchronizable = TransformComponentSync(
        position,
        size,
        rotationDeg,
        parent?.get(ID.mapper)?.id,
    )

    data class TransformComponentSync(
        val position: Vector3?,
        val size: Vector2?,
        val rotation: Float?,
        val parent: String?
    ) : Synchronizable {
        override fun getMapper(): ComponentMapper<out Component> = mapper

        override fun initialize(entity: EngineEntity) {
            entity.with<TransformComponent> {
                synchronize(this@TransformComponentSync)
            }
        }
    }

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}

fun Entity.obtainTransform() = this[TransformComponent.mapper]
    ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")