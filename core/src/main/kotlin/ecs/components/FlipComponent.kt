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

class FlipComponent : SynchronizedComponent, Synchronizable {
    var flipped = false
    @Transient
    var status = 1f

    override fun reset() {
        flipped = false
    }

    override fun synchronize(data: Serializable) {
        if (data !is FlipComponent) return

        flipped = data.flipped
    }

    override fun toSync(): Synchronizable = this
    override fun getMapper(): ComponentMapper<out Component> = mapper

    override fun initialize(entity: EngineEntity) {
        entity.with<FlipComponent> {
            synchronize(this)
        }
    }

    companion object {
        val mapper = mapperFor<FlipComponent>()
    }
}

fun Entity.obtainFlip() = this[FlipComponent.mapper]
    ?: throw KotlinNullPointerException("No FlipComponent given for entity $this")
