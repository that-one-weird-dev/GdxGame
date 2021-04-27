package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ecs.EntityComponent
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable

class PlayerComponent : EntityComponent {

    override fun reset() {
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}

fun Entity.obtainPlayer() = this[PlayerComponent.mapper]
        ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")
