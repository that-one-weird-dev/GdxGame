package client

import Game
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import packets.Packet

class PacketClientHandler(val game: Game) : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is Packet) return

        msg.execute(game)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}