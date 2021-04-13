package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.skye.test.gdxtest.ecs.components.*
import ktx.ashley.allOf
import java.lang.Float.max
import kotlin.math.min

private const val FLIP_SPEED = 15f

class FlipSystem : IteratingSystem(allOf(MoveComponent::class, GraphicComponent::class, FlipComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val move = entity.obtainMove()
        val graphic = entity.obtainGraphic()
        val flip = entity.obtainFlip()

        val xSpeed = move.speed.x
        if (xSpeed != 0f)
            flip.flipped = xSpeed < 0f

        val finalStatus = if (flip.flipped) -1f else 1f
        if (flip.status < finalStatus)
            flip.status = min(flip.status + FLIP_SPEED * deltaTime, 1f)
        else if (flip.status > finalStatus)
            flip.status = max(flip.status - FLIP_SPEED * deltaTime, -1f)

        graphic.sprite.setOriginCenter()
        graphic.sprite.setScale(flip.status, graphic.sprite.scaleY)
    }
}