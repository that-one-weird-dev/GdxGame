import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

private const val PORT = 8000

fun main() {
    val group = NioEventLoopGroup()
    try {
        val bootstrap = Bootstrap()
        bootstrap
            .group(group)
            .channel(NioSocketChannel::class.java)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline().addLast(EchoClientHandler())
                }
            })

        val f = bootstrap.connect("localhost", PORT).sync()
        f.channel().closeFuture().sync()
    } finally {
        group.shutdownGracefully()
    }
}