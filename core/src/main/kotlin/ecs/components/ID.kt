package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor
import java.io.Serializable

class ID : Component, Pool.Poolable, Serializable {
    var id: String = ""

    override fun reset() {
        id = ""
    }

    companion object {
        val mapper = mapperFor<ID>()
    }
}

fun Entity.obtainId() = this[ID.mapper]
    ?: throw KotlinNullPointerException("No ID given for entity $this")
