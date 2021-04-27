package ecs.components.synchronization

import com.badlogic.gdx.utils.ObjectMap
import ecs.components.network.SynchronizedComponent
import java.lang.reflect.Field
import java.lang.reflect.Method

object ComponentPacker {
    val cache = ObjectMap<Class<*>, ComponentReflection>()

    fun get(cl: Class<*>): ComponentReflection = cache[cl] ?: addReflection(cl)

    private fun addReflection(cl: Class<*>): ComponentReflection {
        val list = mutableListOf<Field>()
        var onSyncMethod: Method? = null

        cl.declaredFields.forEach { field ->
            if (field.annotations.find { it is Sync } == null) return@forEach

            field.trySetAccessible()
            list.add(field)
        }

        cl.declaredMethods.forEach { method ->
            if (method.annotations.find { it is OnSync } == null) return@forEach

            method.trySetAccessible()
            onSyncMethod = method
        }

        return ComponentReflection(
            list.toTypedArray(),
            onSyncMethod,
        )
    }

    fun invokeOnSync(obj: Any, data: Map<String, Any?>) {
        get(obj::class.java).onSyncMethod?.invoke(obj, data)
    }
}

fun SynchronizedComponent.pack(): Map<String, Any?> {
    val cl = this::class.java
    val crc = ComponentPacker.get(cl)

    return crc.fields.associate { it.name to it.get(this) }
}