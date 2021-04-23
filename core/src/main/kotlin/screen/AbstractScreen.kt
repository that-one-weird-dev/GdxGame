package screen

import GdxTest
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen

abstract class AbstractScreen(
    val game: GdxTest,
    val gameViewport: Viewport = game.gameViewport,
    val batch: Batch = game.batch,
    val engine: Engine = game.engine,
) : KtxScreen {

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}