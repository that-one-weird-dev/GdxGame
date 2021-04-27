package ecs.components.synchronization

import com.badlogic.gdx.utils.ObjectMap
import ecs.EntityComponent
import java.lang.reflect.Field
import java.lang.reflect.Method

object ComponentPacker {
    private val cache = ObjectMap<Class<*>, ComponentReflection>()

    fun get(cl: Class<*>): ComponentReflection = cache[cl] ?: addReflection(cl)

    // TODO: Implement arguments
    fun callMethod(obj: Any, name: String) {
        get(obj::class.java).methods[name]?.invoke(obj)
    }

    private fun addReflection(cl: Class<*>): ComponentReflection {
        val fieldSetters = mutableMapOf<String, ComponentReflection.FieldSetter>()
        val methods = mutableMapOf<String, Method>()

        cl.declaredFields.forEach { field ->
            if (field.annotations.find { it is Sync } == null) return@forEach

            val setter: Method
            try {
                setter = cl.getDeclaredMethod("set${field.name.capitalize()}", field.type)
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
                return@forEach
            }

            field.trySetAccessible()
            fieldSetters[field.name] = ComponentReflection.FieldSetter(
                field,
                setter,
            )
        }

        cl.declaredMethods.forEach { method ->
            if (method.annotations.find { it is SyncMethod } == null) return@forEach

            method.trySetAccessible()
            methods[method.name] = method
        }

        return ComponentReflection(
            fieldSetters,
            methods,
        )
    }
}

fun EntityComponent.pack(): Map<String, Any?> {
    val cl = this::class.java
    val crc = ComponentPacker.get(cl)

    return crc.fields.mapValues {
        it.value.field.get(this)
    }
}