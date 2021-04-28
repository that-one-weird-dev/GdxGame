package screen

import Game
import client.Client
import ktx.log.debug
import ktx.log.logger
import kotlin.math.min

private val LOG = logger<MainScreen>()

private const val MAX_DELTA_TIME = 1 / 20f

class MainScreen(game: Game) : AbstractScreen(game) {

    private val client = Client("localhost", 8000, game)


    override fun show() {
        LOG.debug { "Main screen shown" }

        client.start()
    }

    override fun render(delta: Float) {
        engine.update(min(MAX_DELTA_TIME, delta))
    }
}