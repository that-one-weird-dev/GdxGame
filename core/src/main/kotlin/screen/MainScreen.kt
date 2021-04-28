package screen

import Game
import GameAnimation
import ecs.components.*
import ktx.log.debug
import ktx.log.logger
import packets.PacketCallMethod
import packets.PacketCreateEntity
import packets.with
import kotlin.math.min
import kotlin.reflect.typeOf

private val LOG = logger<MainScreen>()

private const val MAX_DELTA_TIME = 1 / 20f

class MainScreen(game: Game) : AbstractScreen(game) {

    override fun show() {
        LOG.debug { "Main screen shown" }

        val packet = PacketCreateEntity.create {
            with<IDComponent> {
                id = "down"
            }
            with<TransformComponent> {
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

        val callPacket = PacketCallMethod(
            "down",
            TransformComponent::class.java,
            "test",
            arrayOf("esatto")
        )
        callPacket.execute(game)
    }

    override fun render(delta: Float) {
        engine.update(min(MAX_DELTA_TIME, delta))
    }
}