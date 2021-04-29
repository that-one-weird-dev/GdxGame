package ecs.components

import Game
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ecs.EntityComponent
import ecs.components.synchronization.Sync
import ecs.components.synchronization.SyncMethod
import ktx.ashley.get
import ktx.ashley.mapperFor
import kotlin.math.abs

class TransformComponent : EntityComponent, Comparable<TransformComponent> {
    @Sync
    var position = Vector3()
        set(value) {
            field.set(value)
        }
    @Sync
    var globalPosition = Vector3()
        set(value) {
            field.set(value)
        }

    @Sync
    var size = Vector2(1f, 1f)
        set(value) {
            field.set(value)
        }
    @Sync
    var rotationDeg = 0f

    var parent: Entity? = null


    @SyncMethod
    fun syncSetPosition(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
    }

    @SyncMethod
    fun syncSetPositionRounded(x: Float, y: Float, z: Float) {
        if (abs(position.x - x) > .5f)
            position.x = x
        if (abs(position.y - y) > .5f)
            position.y = y
        if (abs(position.z - z) > .5f)
            position.z = z
    }

    @SyncMethod
    fun syncSetParent(id: String) {
        parent = Game.entities[id]
    }

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