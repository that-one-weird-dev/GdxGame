package ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ecs.components.network.Synchronizable
import ecs.components.network.SynchronizedComponent
import ktx.ashley.EngineEntity
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.ashley.with
import java.io.Serializable

class GraphicComponent : SynchronizedComponent, Synchronizable {
    @Transient
    val sprite = Sprite()

    override fun reset() {
        sprite.texture = null
        sprite.setColor(1f, 1f, 1f, 1f)
    }

    override fun synchronize(data: Serializable) {
    }

    override fun toSync(): Synchronizable = this

    fun setSpriteRegion(region: TextureRegion) {
        sprite.setRegion(region)
    }

    companion object {
        val mapper = mapperFor<GraphicComponent>()
    }

    override fun getMapper(): ComponentMapper<out Component> = mapper
    override fun initialize(entity: EngineEntity) {
        entity.with<GraphicComponent> {
            synchronize(this)
        }
    }
}

fun Entity.obtainGraphic() = this[GraphicComponent.mapper]
    ?: throw KotlinNullPointerException("No GraphicComponent given for entity $this")
