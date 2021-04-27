package ecs.components

import com.badlogic.ashley.core.Entity
import ecs.EntityComponent
import ecs.components.synchronization.Sync
import ktx.ashley.get
import ktx.ashley.mapperFor

class IDComponent : EntityComponent {
    @Sync
    var id: String = ""

    override fun reset() {
        id = ""
    }

    companion object {
        val mapper = mapperFor<IDComponent>()
    }
}

fun Entity.obtainId() = this[IDComponent.mapper]
    ?: throw KotlinNullPointerException("No ID given for entity $this")
