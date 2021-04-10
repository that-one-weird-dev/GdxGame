package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.skye.test.gdxtest.ecs.components.MoveComponent
import com.skye.test.gdxtest.ecs.components.TransformComponent
import com.skye.test.gdxtest.ecs.components.obtainMove
import com.skye.test.gdxtest.ecs.components.obtainTransform
import ktx.ashley.allOf


class SmoothMoveSystem : IteratingSystem(allOf(MoveComponent::class, TransformComponent::class).get()) {
    private var accumulator = 0f
    private var alpha = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while (accumulator >= MOVE_SYSTEM_UPDATE_RATE)
            accumulator -= MOVE_SYSTEM_UPDATE_RATE

        alpha = accumulator / MOVE_SYSTEM_UPDATE_RATE

        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val move = entity.obtainMove()
        val transform = entity.obtainTransform()

        move.interpolatedPosition.set(
            MathUtils.lerp(move.prevPosition.x, transform.position.x, alpha),
            MathUtils.lerp(move.prevPosition.y, transform.position.y, alpha),
            transform.position.z
        )
    }
}