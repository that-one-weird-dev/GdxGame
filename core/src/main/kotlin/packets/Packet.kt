package packets

import Game
import java.io.Serializable

interface Packet : Serializable {
    fun execute(game: Game)
}