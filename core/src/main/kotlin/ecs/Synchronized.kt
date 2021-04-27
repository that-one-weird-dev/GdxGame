package ecs

import java.io.Serializable

interface Synchronized {
    fun synchronize(data: Serializable)
}