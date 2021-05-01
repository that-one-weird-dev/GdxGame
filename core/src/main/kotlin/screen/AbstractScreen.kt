package screen

import Game
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen

abstract class AbstractScreen : KtxScreen {
    val gameViewport: Viewport = Game.gameViewport
    val batch: Batch = Game.batch
    val engine: Engine = Game.engine

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}