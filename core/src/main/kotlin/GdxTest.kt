import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.skye.test.gdxtest.AnimationProvider
import com.skye.test.gdxtest.ecs.systems.*
import com.skye.test.gdxtest.screen.AbstractScreen
import com.skye.test.gdxtest.screen.MainScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger


const val V_WIDTH = 16
const val V_HEIGHT = 9

private val LOG = logger<GdxTest>()

class GdxTest : KtxGame<AbstractScreen>() {
    val batch: Batch by lazy { SpriteBatch() }
    val gameViewport by lazy { FitViewport(16f, 9f) }

    val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(PlayerInputSystem(gameViewport))
            addSystem(MoveSystem())
            addSystem(TransformSystem())
            addSystem(SmoothMoveSystem())
            addSystem(AnimationSystem())
            addSystem(FlipSystem())
            addSystem(RenderSystem(batch, gameViewport))

            addSystem(DebugSystem())
        }
    }

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        LOG.debug { "Creating game instance" }

        addScreen(MainScreen(this))
        setScreen<MainScreen>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
        AnimationProvider.dispose()
    }
}