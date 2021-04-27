package ecs.components

import Game
import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ecs.EntityComponent
import ecs.components.synchronization.Sync
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable

class TransformComponent : EntityComponent, Comparable<TransformComponent> {
    @Sync
    val position = Vector3()
    @Sync
    val globalPosition = Vector3()

    @Sync
    val size = Vector2(1f, 1f)
    @Sync
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

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}

fun Entity.obtainTransform() = this[TransformComponent.mapper]
    ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")