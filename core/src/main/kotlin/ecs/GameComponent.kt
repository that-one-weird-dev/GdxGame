package ecs

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

interface GameComponent : Component, Pool.Poolable