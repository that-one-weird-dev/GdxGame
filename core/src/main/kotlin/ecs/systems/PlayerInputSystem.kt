package ecs.systems

import Game
import GameAnimation
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.viewport.Viewport
import ecs.components.*
import ktx.ashley.allOf
import packets.server.SPacketMove


private const val TOLERANCE = .2f
private const val SPEED = 6f

class PlayerInputSystem(
    private val gameViewport: Viewport
) : IteratingSystem(allOf(PlayerComponent::class, MoveComponent::class, AnimationComponent::class).get()) {

    private var movX: Byte = 0
    private var movY: Byte = 0

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val moveComp = entity.obtainMove()
        val animComp = entity.obtainAnimation()

        val speedY = when {
            Gdx.input.isKeyPressed(Input.Keys.W) -> SPEED
            Gdx.input.isKeyPressed(Input.Keys.S) -> -SPEED
            else -> 0f
        }
        val speedX = when {
            Gdx.input.isKeyPressed(Input.Keys.D) -> SPEED
            Gdx.input.isKeyPressed(Input.Keys.A) -> -SPEED
            else -> 0f
        }

        moveComp.speed.set(speedX, speedY)

        checkMove(
            if (speedX > 0) 1 else if (speedX < 0) -1 else 0,
            if (speedY > 0) 1 else if (speedY < 0) -1 else 0,
        )

        val anim = if(speedX != 0f || speedY != 0f) GameAnimation.PLAYER_RUN else GameAnimation.PLAYER_IDLE

        if (animComp.currentAnimation != anim)
            animComp.currentAnimation = anim
    }

    private fun checkMove(x: Byte, y: Byte) {
        if (movX != x || movY != y) {
            Game.client.send(SPacketMove(x, y))

            movX = x
            movY = y
        }
    }
}
