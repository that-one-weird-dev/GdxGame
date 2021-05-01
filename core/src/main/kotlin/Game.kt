import client.Client
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ecs.components.obtainId
import ecs.systems.*
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger
import screen.AbstractScreen
import screen.MainScreen


const val V_WIDTH = 16
const val V_HEIGHT = 9

private val LOG = logger<Game>()

object Game: KtxGame<AbstractScreen>() {
    val batch: Batch by lazy { SpriteBatch() }
    val gameViewport by lazy { FitViewport(16f, 9f) }

    val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(PlayerInputSystem())
            addSystem(MoveSystem())
            addSystem(TransformSystem())
            addSystem(SmoothMoveSystem())
            addSystem(AnimationSystem())
            addSystem(FlipSystem())
            addSystem(RenderSystem(batch, gameViewport))

            addSystem(DebugSystem())
        }
    }

    val entities = mutableMapOf<String, Entity>()

    val client = Client("localhost", 8000)

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        LOG.debug { "Creating game instance" }
        
        engine.addEntityListener(object : EntityListener {
            override fun entityAdded(entity: Entity) {
                val id = entity.obtainId()
                entities[id.id] = entity
            }
            override fun entityRemoved(entity: Entity) {
                val id = entity.obtainId()
                entities.remove(id.id)
            }
        })

        client.start()

        addScreen(MainScreen())
        setScreen<MainScreen>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
        AnimationProvider.dispose()
    }
}