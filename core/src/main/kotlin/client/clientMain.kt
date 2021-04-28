package client

import Game
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.serialization.ClassResolvers
import io.netty.handler.codec.serialization.ObjectDecoder
import io.netty.handler.codec.serialization.ObjectEncoder
import java.lang.Exception


class Client(
    val ip: String,
    val port: Int,
    private val game: Game,
) {
    private val group = NioEventLoopGroup()
    private val bootstrap = Bootstrap().apply {
        group(group)
            .channel(NioSocketChannel::class.java)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline().addLast(
                        ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())),
                        ObjectEncoder(),
                        PacketClientHandler(game),
                    )
                }
            })
    }
    private var connection: Channel? = null

    fun start(): ChannelFuture {
        connection = bootstrap.connect(ip, port).channel()
        return connection?.closeFuture() ?: throw Exception("Unexpected error in client while connecting")
    }

    fun close(): ChannelFuture {
        return connection?.disconnect() ?: throw Exception("Tried disconnecting while not connected")
    }
}