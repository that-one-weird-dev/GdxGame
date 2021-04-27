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

class ID : SynchronizedComponent, Synchronizable {
    var id: String = ""

    override fun reset() {
        id = ""
    }

    override fun synchronize(data: Serializable) {
        if (data !is ID) return

        this.id = data.id
    }

    override fun toSync(): Synchronizable = this

    override fun getMapper(): ComponentMapper<out Component> = mapper
    override fun initialize(entity: EngineEntity) {
        entity.with<ID> {
            synchronize(this)
        }
    }

    companion object {
        val mapper = mapperFor<ID>()
    }
}

fun Entity.obtainId() = this[ID.mapper]
    ?: throw KotlinNullPointerException("No ID given for entity $this")
