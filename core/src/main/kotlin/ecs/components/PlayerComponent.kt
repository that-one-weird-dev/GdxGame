package com.skye.test.gdxtest.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

const val MAX_LIFE = 100f

class PlayerComponent : Component, Pool.Poolable {

    override fun reset() {
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}

fun Entity.obtainPlayer() = this[PlayerComponent.mapper]
        ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")
