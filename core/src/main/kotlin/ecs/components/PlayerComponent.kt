package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ecs.components.network.Synchronizable
import ecs.components.network.SynchronizedComponent
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable

const val MAX_LIFE = 100f

class PlayerComponent : SynchronizedComponent, Synchronizable {

    override fun reset() {
    }

    override fun synchronize(data: Serializable) {}
    override fun toSync(): Synchronizable = this

    override fun getMapper(): ComponentMapper<out Component> = mapper
    override fun initialize(entity: EngineEntity) {
        entity.with<PlayerComponent> {
            synchronize(this)
        }
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}

fun Entity.obtainPlayer() = this[PlayerComponent.mapper]
        ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")
