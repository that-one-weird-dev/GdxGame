package ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ecs.components.AnimationComponent
import ecs.components.GraphicComponent
import ecs.components.obtainAnimation
import ecs.components.obtainGraphic
import ktx.ashley.allOf

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