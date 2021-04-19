package world

import entities.player.Player

abstract class World {
    protected val players = listOf<Player>()
}