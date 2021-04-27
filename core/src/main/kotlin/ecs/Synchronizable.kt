package ecs

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import java.io.Serializable

interface Synchronizable<T: Component> : Serializable {
    val mapper: ComponentMapper<T>
}