package ecs.components.network

import java.io.Serializable

interface Synchronized {
    fun synchronize(data: Serializable)
    fun toSync(): Synchronizable
}