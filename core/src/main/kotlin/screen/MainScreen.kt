package screen

import AnimationProvider
import GameAnimation
import Game
import com.badlogic.gdx.math.Vector3
import ecs.components.*
import ecs.components.synchronization.callMethod
import ecs.components.synchronization.pack
import ecs.components.synchronization.setField
import ecs.createEntityWithId
import ecs.systems.SmoothMoveSystem
import ktx.ashley.with
import ktx.log.debug
import ktx.log.logger
import packets.PacketCreateEntity
import packets.with
import kotlin.math.min

private val LOG = logger<MainScreen>()

private const val MAX_DELTA_TIME = 1 / 20f

class MainScreen(game: Game) : AbstractScreen(game) {

    override fun show() {
        LOG.debug { "Main screen shown" }

        val packet = PacketCreateEntity.create {
            with<IDComponent> {
                id = "down"
            }
            with<TransformComponent>() {
                setInitialPosition(8f, 4.5f, 0f)
                size.set(2.5f, 2.5f)
            }
            with<AnimationComponent> {
                currentAnimation = GameAnimation.PLAYER_IDLE
            }
            with<GraphicComponent>()
            with<PlayerComponent>()
            with<MoveComponent>()
            with<FlipComponent>()
        }
        packet.execute(game)

    override fun render(delta: Float) {
        engine.update(min(MAX_DELTA_TIME, delta))
    }

    override fun dispose() {
        super.dispose()
    }
}