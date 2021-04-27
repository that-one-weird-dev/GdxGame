package ecs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import ktx.ashley.EngineEntity
import java.io.Serializable

interface Synchronizable : Serializable {
    // This is a function and not a attribute to avoid making it not transient or not a getter
    fun getMapper(): ComponentMapper<out Component>

    fun initialize(entity: EngineEntity)
}