import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class EchoClientHandler: ChannelInboundHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        val buffer = Unpooled.buffer(4, 4)
        for (char in "helo")
            buffer.writeByte(char.toInt())

        ctx.writeAndFlush(buffer)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is ByteBuf) return

        while (msg.isReadable)
            print(msg.readByte().toChar())
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}
