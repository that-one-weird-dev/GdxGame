package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor


class MoveComponent : Component, Pool.Poolable {
    val speed = Vector2()
    val prevPosition = Vector3()
    val interpolatedPosition = Vector3()

    override fun reset() {
        speed.set(0f, 0f)
    }

    companion object {
        val mapper = mapperFor<MoveComponent>()
    }
}

fun Entity.obtainMove() = this[MoveComponent.mapper]
    ?: throw KotlinNullPointerException("No MoveComponent given for entity $this")
