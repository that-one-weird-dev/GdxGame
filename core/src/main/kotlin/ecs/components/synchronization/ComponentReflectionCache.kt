package ecs.components.synchronization

import java.lang.reflect.Field
import java.lang.reflect.Method

class ComponentReflection(
    val fields: Array<Field> = arrayOf<Field>(),
    val onSyncMethod: Method? = null,
)