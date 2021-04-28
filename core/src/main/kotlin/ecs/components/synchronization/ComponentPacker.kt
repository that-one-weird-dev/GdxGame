package ecs.components.synchronization

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.ObjectMap
import ecs.EntityComponent
import java.lang.reflect.Method

object ComponentPacker {
    private val cache = ObjectMap<Class<out EntityComponent>, ComponentReflection>()

    fun get(cl: Class<out EntityComponent>): ComponentReflection = cache[cl] ?: addReflection(cl)

    fun callMethod(obj: EntityComponent, name: String, vararg args: Any?) {
        get(obj::class.java).methods[name]?.invoke(obj, *args)
    }

    fun packComponent(obj: EntityComponent): Map<String, Any?> {
        val cl = obj::class.java

        return get(cl).fields.mapValues {
            it.value.field.get(obj)
        }
    }

    fun setField(obj: EntityComponent, name: String, value: Any?) {
        get(obj::class.java).fields[name]?.setter?.invoke(obj, value)
    }

    fun getMapper(cl: Class<out EntityComponent>): ComponentMapper<out Component> {
        return get(cl).mapper
    }

    private fun addReflection(cl: Class<out EntityComponent>): ComponentReflection {
        val fieldSetters = mutableMapOf<String, ComponentReflection.FieldSetter>()
        val methods = mutableMapOf<String, Method>()

        val mapper = try {
            val companion = cl.getField("Companion").get(null)
            companion::class.java.getMethod("getMapper").invoke(companion) as ComponentMapper<out Component>
        } catch (e: Exception) {
            throw NoMapperFoundInComponent(cl)
        }

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
            mapper
        )
    }
}

fun EntityComponent.pack(): Map<String, Any?> = ComponentPacker.packComponent(this)
fun EntityComponent.callMethod(name: String, vararg args: Any?) = ComponentPacker.callMethod(this, name, args = args)
fun EntityComponent.setField(name: String, value: Any?) = ComponentPacker.setField(this, name, value)
fun EntityComponent.getMapper() = ComponentPacker.getMapper(this::class.java)