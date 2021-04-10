package com.skye.test.gdxtest.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

const val MAX_LIFE = 100f

class PlayerComponent : Component, Pool.Poolable {
    var life = MAX_LIFE
    var maxLife = MAX_LIFE
    var distance = 0f

    override fun reset() {
        life = MAX_LIFE
        maxLife = MAX_LIFE
        distance = 0f
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}

fun Entity.obtainPlayer() = this[PlayerComponent.mapper]
        ?: throw KotlinNullPointerException("No TransformComponent given for entity $this")
