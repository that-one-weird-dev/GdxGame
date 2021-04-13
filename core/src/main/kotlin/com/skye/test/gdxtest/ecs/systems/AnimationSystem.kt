package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.skye.test.gdxtest.ecs.components.AnimationComponent
import com.skye.test.gdxtest.ecs.components.GraphicComponent
import com.skye.test.gdxtest.ecs.components.obtainAnimation
import com.skye.test.gdxtest.ecs.components.obtainGraphic
import ktx.ashley.allOf
import ktx.log.debug

class AnimationSystem : IteratingSystem(allOf(AnimationComponent::class, GraphicComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val animComp = entity.obtainAnimation()
        val graphic = entity.obtainGraphic()

        animComp.stateTime += deltaTime

        animComp.animation?.getKeyFrame(animComp.stateTime)?.let {
            graphic.setSpriteRegion(it)
        }
    }
}