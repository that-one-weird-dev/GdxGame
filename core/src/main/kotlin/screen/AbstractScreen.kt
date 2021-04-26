package screen

import Game
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ecs.components.ID
import ktx.app.KtxScreen
import ktx.ashley.EngineEntity
import ktx.ashley.entity
import ktx.ashley.with
import java.util.*

abstract class AbstractScreen(
    val game: Game,
    val gameViewport: Viewport = game.gameViewport,
    val batch: Batch = game.batch,
    val engine: Engine = game.engine,
) : KtxScreen {

    inline fun createEntity(configure: EngineEntity.() -> Unit): Entity {
        return engine.entity {
            with<ID> {
                id = UUID.randomUUID().toString()
            }
            configure()
        }
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}