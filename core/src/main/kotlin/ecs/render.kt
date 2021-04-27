package ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import ecs.components.IDComponent
import ktx.ashley.EngineEntity
import ktx.ashley.entity
import ktx.ashley.with
import java.util.*


inline fun Engine.createEntityWithId(
    id: String = UUID.randomUUID().toString(),
    configure: EngineEntity.() -> Unit
): Entity {
    return entity {
        with<IDComponent> {
            this.id = id
        }
        configure()
    }
}

