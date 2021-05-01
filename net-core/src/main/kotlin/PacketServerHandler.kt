import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import ktx.log.debug
import ktx.log.logger
import packets.ServerPacket
import packets.client.PacketCreateEntity


private val LOG = logger<PacketCreateEntity>()

class PacketServerHandler : ChannelInboundHandlerAdapter() {

    private val pir = Application.pir

    override fun channelActive(ctx: ChannelHandlerContext) {
        LOG.debug { "Someone connected" }
        Application.entities.forEach {
            ctx.write(PacketCreateEntity.fromEntity(it.value))
        }
        ctx.flush()
    }


    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is ServerPacket) return

        // Should never fail because of the check before
        val method = pir.get(msg::class.java as Class<out ServerPacket>)
        method?.invoke(msg, msg, ctx)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}
