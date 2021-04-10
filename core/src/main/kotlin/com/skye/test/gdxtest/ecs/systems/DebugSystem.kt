package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.skye.test.gdxtest.ecs.components.PlayerComponent
import com.skye.test.gdxtest.ecs.components.TransformComponent
import com.skye.test.gdxtest.ecs.components.obtainPlayer
import com.skye.test.gdxtest.ecs.components.obtainTransform
import ktx.ashley.allOf
import ktx.ashley.getSystem
import ktx.log.debug

private const val DEBUG_INTERVAL = .25f
private const val DEBUG_ENABLED = true

class DebugSystem :
    IntervalIteratingSystem(allOf(PlayerComponent::class, TransformComponent::class).get(), DEBUG_INTERVAL) {
    init {
        setProcessing(DEBUG_ENABLED)
    }

    override fun processEntity(entity: Entity) {
        val transform = entity.obtainTransform()

        when {
            Gdx.input.isKeyPressed(Input.Keys.NUM_1) -> {
                // Stop entities movement
                val moveSystem = engine.getSystem<MoveSystem>()
                moveSystem.setProcessing(false)
            }
            Gdx.input.isKeyPressed(Input.Keys.NUM_2) -> {
                // Stop entities movement
                val moveSystem = engine.getSystem<MoveSystem>()
                moveSystem.setProcessing(true)
            }
        }

        Gdx.graphics.setTitle("Test Game DEBU - pos:${transform.position}")
    }
}