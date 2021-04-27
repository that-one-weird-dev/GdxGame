package ecs.components

import Animation2D
import AnimationProvider
import GameAnimation
import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ecs.components.network.Synchronizable
import ecs.components.network.SynchronizedComponent
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable


class AnimationComponent : SynchronizedComponent, Synchronizable {
    var currentAnimation: GameAnimation = GameAnimation.NONE
        set(value) {
            animation = AnimationProvider.getAnimation(value)
            field = value
        }

    @Transient
    var animation: Animation2D? = null
    @Transient
    var stateTime = 0f

    override fun reset() {
        stateTime = 0f
    }

    override fun toSync(): Synchronizable = this
    override fun synchronize(data: Serializable) {
        if (data !is AnimationComponent) return

        this.currentAnimation = data.currentAnimation
    }

    override fun getMapper(): ComponentMapper<out Component> = mapper
    override fun initialize(entity: EngineEntity) {
        entity.with<AnimationComponent> {
            synchronize(this)
        }
    }

    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}

fun Entity.obtainAnimation() = this[AnimationComponent.mapper]
    ?: throw KotlinNullPointerException("No ecs.components.AnimationComponent given for entity $this")
