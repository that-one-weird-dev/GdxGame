package ecs.components.synchronization

import java.lang.reflect.Field
import java.lang.reflect.Method

class ComponentReflection(
    val fields: Map<String, FieldSetter> = mapOf(),
    val methods: Map<String, Method> = mapOf(),
) {
    data class FieldSetter(val field: Field, val setter: Method)
}