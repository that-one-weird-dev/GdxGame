package packets

import Game
import java.io.Serializable

interface ClientPacket : Serializable {
    fun execute(game: Game)
}