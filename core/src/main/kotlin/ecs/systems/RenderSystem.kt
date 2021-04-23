package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.skye.test.gdxtest.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger


private val LOG = logger<RenderSystem>()

class RenderSystem(
    private val batch: Batch,
    private val gameViewport: Viewport,
) : SortedIteratingSystem(
    allOf(TransformComponent::class, GraphicComponent::class).get(),
    compareBy { entity -> entity[TransformComponent.mapper] }
) {

    override fun update(deltaTime: Float) {
        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.obtainTransform()
        val graphic = entity.obtainGraphic()
        val move = entity[MoveComponent.mapper]

        graphic.sprite.texture ?: return LOG.error {"Error loading sprite texture"}

        graphic.sprite.run {
            rotation = transform.rotationDeg

            // Use interpolatedPosition if the entity has the moveComponent otherwise use globalPosition
            val position = move?.interpolatedPosition ?: transform.globalPosition
            setBounds(position.x, position.y, transform.size.x, transform.size.y)
            draw(batch)
        }
    }
}