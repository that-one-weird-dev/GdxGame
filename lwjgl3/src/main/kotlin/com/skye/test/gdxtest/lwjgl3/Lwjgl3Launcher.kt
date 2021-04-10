import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.skye.test.gdxtest.GdxTest

fun main() {
    Lwjgl3Application(GdxTest(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("KTX test")
        setWindowedMode(16 * 75, 9 * 75)
        setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
    })
}