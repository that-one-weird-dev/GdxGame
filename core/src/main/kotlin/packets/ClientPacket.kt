package packets

import java.io.Serializable

interface ClientPacket : Serializable {
    fun execute()
}