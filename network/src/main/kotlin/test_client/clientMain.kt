import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.serialization.ClassResolvers
import io.netty.handler.codec.serialization.ObjectDecoder
import io.netty.handler.codec.serialization.ObjectEncoder

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
                    ch.pipeline().addLast(
                        ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())),
                        ObjectEncoder(),
                        EchoClientHandler(),
                    )
                }
            })

        val f = bootstrap.connect("localhost", PORT).sync()
        f.channel().closeFuture().sync()
    } finally {
        group.shutdownGracefully()
    }
}