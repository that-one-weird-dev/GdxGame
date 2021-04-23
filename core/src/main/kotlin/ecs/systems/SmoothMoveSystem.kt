package ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import ecs.components.MoveComponent
import ecs.components.TransformComponent
import ecs.components.obtainMove
import ecs.components.obtainTransform
import ktx.ashley.allOf


class SmoothMoveSystem : IteratingSystem(allOf(MoveComponent::class, TransformComponent::class).get()) {
    private var accumulator = 0f
    private var alpha = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while (accumulator >= MOVE_SYSTEM_UPDATE_RATE)
            accumulator -= MOVE_SYSTEM_UPDATE_RATE

        alpha = accumulator / MOVE_SYSTEM_UPDATE_RATE

        super.update(MOVE_SYSTEM_UPDATE_RATE)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val move = entity.obtainMove()
        val transform = entity.obtainTransform()

        move.interpolatedPosition.set(
            MathUtils.lerp(move.prevPosition.x, transform.globalPosition.x, alpha),
            MathUtils.lerp(move.prevPosition.y, transform.globalPosition.y, alpha),
            transform.position.z
        )
    }
}