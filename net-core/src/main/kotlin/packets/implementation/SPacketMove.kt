package packets.implementation

import io.netty.channel.ChannelHandlerContext
import packets.reflection.Implement
import packets.server.SPacketMove

@Implement
fun SPacketMove.execute(ctx: ChannelHandlerContext) {
    println("Executing SPacketMove")
}