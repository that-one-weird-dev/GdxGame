package ecs.components.synchronization

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import ecs.EntityComponent
import java.lang.reflect.Field
import java.lang.reflect.Method

class ComponentReflection(
    val fields: Map<String, FieldSetter> = mapOf(),
    val methods: Map<String, Method> = mapOf(),
    val mapper: ComponentMapper<out Component>,
) {
    data class FieldSetter(val field: Field, val setter: Method)
}