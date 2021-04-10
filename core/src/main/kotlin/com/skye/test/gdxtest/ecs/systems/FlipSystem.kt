package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.skye.test.gdxtest.ecs.components.*
import ktx.ashley.allOf


class FlipSystem : IteratingSystem(allOf(MoveComponent::class, GraphicComponent::class, FlipComponent::class).get()) {

    var set = false
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val move = entity.obtainMove()
        val graphic = entity.obtainGraphic()
        val flip = entity.obtainFlip()

        val xSpeed = move.speed.x
        if (xSpeed != 0f)
            flip.flipped = xSpeed < 0f

        graphic.sprite.flip(
            flip.flipped,
            false
        )
    }
}