import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.io.Serializable
import kotlin.reflect.typeOf

class EchoClientHandler : ChannelInboundHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(Test("ciao"))
        println("Packet sent")
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println("Received: ${msg.javaClass.name}")
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}


data class Test(val message: String) : Serializable