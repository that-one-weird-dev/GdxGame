import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import ktx.log.debug
import ktx.log.logger
import packets.PacketCreateEntity


private val LOG = logger<PacketCreateEntity>()

class PacketServerHandler(private val app: Application) : ChannelInboundHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        LOG.debug { "Someone connected" }
        app.entities.forEach {
            ctx.write(PacketCreateEntity.fromEntity(it.value))
        }
        ctx.flush()
    }


    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        ctx.write(msg)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}
