package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor
import java.io.Serializable

class FlipComponent : Component, Pool.Poolable, Serializable {
    var flipped = false
    @Transient
    var status = 1f

    override fun reset() {
        flipped = false
    }

    companion object {
        val mapper = mapperFor<FlipComponent>()
    }
}

fun Entity.obtainFlip() = this[FlipComponent.mapper]
    ?: throw KotlinNullPointerException("No FlipComponent given for entity $this")
