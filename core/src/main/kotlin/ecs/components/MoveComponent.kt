package ecs.components

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


class MoveComponent : EntityComponent {
    @Sync
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
