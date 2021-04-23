package com.skye.test.gdxtest.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.skye.test.gdxtest.ecs.components.TransformComponent
import com.skye.test.gdxtest.ecs.components.obtainTransform
import ktx.ashley.allOf
import ktx.ashley.get

// TODO: make this sorted so parents update before children
class TransformSystem : IteratingSystem(allOf(TransformComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.obtainTransform()

        // I know the next lines are redundant but its better for performance
        if (transform.parent == null) {
            transform.globalPosition.set(transform.position)
            return
        }

        val parTransform = transform.parent?.get(TransformComponent.mapper)
        if (parTransform == null) {
            transform.globalPosition.set(transform.position)
            return
        }

        transform.globalPosition.set(parTransform.globalPosition)
        transform.globalPosition.add(transform.position)
    }
}