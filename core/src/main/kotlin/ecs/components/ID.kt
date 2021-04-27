package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ecs.EntityComponent
import ecs.components.synchronization.Sync
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable

class ID : EntityComponent {
    @Sync
    var id: String = ""

    override fun reset() {
        id = ""
    }

    companion object {
        val mapper = mapperFor<ID>()
    }
}

fun Entity.obtainId() = this[ID.mapper]
    ?: throw KotlinNullPointerException("No ID given for entity $this")
