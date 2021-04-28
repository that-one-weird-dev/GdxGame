import ecs.components.*
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import packets.PacketCreateEntity
import packets.with
import kotlin.system.measureTimeMillis

class PacketServerHandler(val app: Application) : ChannelInboundHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        println("someone connected")
        println(app.entities.size)
        val time = measureTimeMillis {
            app.entities.forEach {
                ctx.write(PacketCreateEntity.fromEntity(it.value))
            }
        }
        println(time)
        ctx.flush()
    }


    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println("Received message")
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
