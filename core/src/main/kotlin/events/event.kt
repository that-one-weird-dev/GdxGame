package events

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxSet
import ktx.collections.set
import kotlin.reflect.KClass


sealed class GameEvent {
    object PlayerMove : GameEvent() {
        val posX: Int = 0
        val posY: Int = 0
    }
}

interface GameEventListener {
    fun onEvent(event: GameEvent? = null)
}


object GameEventManager {
    private val listeners = ObjectMap<KClass<out GameEvent>, GdxSet<GameEventListener>>()

    fun registerListener(type: KClass<out GameEvent>, listener: GameEventListener) {
        var set = listeners[type]
        if (set == null) {
            set = GdxSet()
            listeners[type] = set
        }

        set.add(listener)
    }

    fun dispatchEvent(type: KClass<out GameEvent>) {
        listeners[type]?.forEach {
            it.onEvent()
        }
    }
}