package packets.reflection

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.set
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import packets.ServerPacket
import java.lang.Exception
import java.lang.reflect.Method


class PacketImplementationReflection {
    private val cache: ObjectMap<Class<out ServerPacket>, Method> = ObjectMap()

    fun get(cl: Class<out ServerPacket>): Method? {
        return cache[cl]
    }

    fun load() {
        val ref = Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("packets.implementation"))
                .setScanners(MethodAnnotationsScanner())
        )
        val methods = ref.getMethodsAnnotatedWith(Implement::class.java)
        methods.forEach { method ->
            val cl =
                (method.parameterTypes[0]
                    ?: throw Exception("Server packet impolementation has no class as parameter"))
                        as Class<out ServerPacket>

            cache[cl] = method
        }
    }
}