package ecs.components

import Animation2D
import AnimationProvider
import GameAnimation
import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor


class AnimationComponent : Component, Pool.Poolable {
    var currentAnimation: GameAnimation = GameAnimation.NONE
        set(value) {
            animation = AnimationProvider.getAnimation(value)
            field = value
        }

    var animation: Animation2D? = null
    var stateTime = 0f

    override fun reset() {
        stateTime = 0f
    }

    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}

fun Entity.obtainAnimation() = this[AnimationComponent.mapper]
    ?: throw KotlinNullPointerException("No ecs.components.AnimationComponent given for entity $this")
