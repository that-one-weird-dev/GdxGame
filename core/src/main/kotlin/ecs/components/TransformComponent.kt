package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent> {
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

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}

fun Entity.obtainTransform() = this[TransformComponent.mapper]
    ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")