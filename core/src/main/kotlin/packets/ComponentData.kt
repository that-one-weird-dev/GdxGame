package packets

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.reflect.ClassReflection
import ecs.components.synchronization.pack

data class ComponentData(val type: Class<out Component>, val fields: Map<String, Any?>) {
    companion object {
        inline fun <reified T: Component> create(configure: T.() -> Unit = {}): ComponentData {
            val comp = ClassReflection.newInstance(T::class.java)
            comp.configure()

            return ComponentData(
                comp::class.java,
                comp.pack(),
            )
        }
    }
}
