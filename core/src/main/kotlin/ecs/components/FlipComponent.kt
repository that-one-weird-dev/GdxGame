package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ecs.EntityComponent
import ecs.components.synchronization.Sync
import ecs.components.synchronization.SyncMethod
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable

class FlipComponent : EntityComponent {
    @Sync
    var flipped = false
    var status = 1f

    @SyncMethod
    fun testMethod(test: String) {
        println("testMethod: $test")
    }

    override fun reset() {
        flipped = false
    }

    companion object {
        val mapper = mapperFor<FlipComponent>()
    }
}

fun Entity.obtainFlip() = this[FlipComponent.mapper]
    ?: throw KotlinNullPointerException("No FlipComponent given for entity $this")
