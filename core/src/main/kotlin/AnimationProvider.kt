import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import java.util.*


private const val DEFAULT_FRAME_DURATION = 1 / 5f

typealias Animation2D = Animation<TextureAtlas.AtlasRegion>


enum class GameAnimation(
    val atlasKey: String,
    val speed: Float = 1f,
    val mode: Animation.PlayMode = Animation.PlayMode.LOOP,
) {
    NONE(""),
    PLAYER_IDLE("adventurer_idle"),
    PLAYER_RUN("adventurer_run")
}

object AnimationProvider {
    private val animationCache by lazy { EnumMap<GameAnimation, Animation2D>(GameAnimation::class.java) }
    private val graphicAtlas by lazy { TextureAtlas(Gdx.files.internal("graphics/Graphics.atlas")) }

    fun getAnimation(gAnim: GameAnimation): Animation2D? {
        if (gAnim == GameAnimation.NONE) return null

        return animationCache[gAnim] ?: Animation(
            DEFAULT_FRAME_DURATION / gAnim.speed,
            graphicAtlas.findRegions(gAnim.atlasKey),
            gAnim.mode
        )
    }

    fun dispose() {
        graphicAtlas.dispose()
    }
}