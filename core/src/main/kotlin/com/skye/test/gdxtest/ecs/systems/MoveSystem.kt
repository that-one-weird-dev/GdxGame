package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.skye.test.gdxtest.V_HEIGHT
import com.skye.test.gdxtest.V_WIDTH
import com.skye.test.gdxtest.ecs.components.*
import com.skye.test.gdxtest.events.GameEvent
import com.skye.test.gdxtest.events.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get


const val MOVE_SYSTEM_UPDATE_RATE = 1 / 25f

class MoveSystem : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).get()) {
    private var accumulator = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime

        while (accumulator >= MOVE_SYSTEM_UPDATE_RATE) {
            accumulator -= MOVE_SYSTEM_UPDATE_RATE
            super.update(MOVE_SYSTEM_UPDATE_RATE)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.obtainTransform()
        val move = entity.obtainMove()

        move.prevPosition.set(transform.globalPosition)

        moveEntity(transform, move, deltaTime)

        entity[PlayerComponent.mapper]?.let {
            GameEventManager.dispatchEvent(GameEvent.PlayerMove::class)
        }
    }

    // Normally moves the local position of the entity
    private fun moveEntity(transform: TransformComponent, move: MoveComponent, deltaTime: Float) {
        transform.position.x = MathUtils.clamp(
            transform.position.x + move.speed.x * deltaTime,
            0f,
            V_WIDTH - transform.size.x
        )

        transform.position.y = MathUtils.clamp(
            transform.position.y + move.speed.y * deltaTime,
            0f,
            V_HEIGHT - transform.size.y
        )
    }
}