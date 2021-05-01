package client

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
import packets.ClientPacket
import packets.ServerPacket


class Client(
    val ip: String,
    val port: Int,
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
                        PacketClientHandler(),
                    )
                }
            })
    }
    var connection: Channel? = null
        private set


    fun send(packet: ServerPacket) {
        connection?.write(packet)
    }

    fun flush() {
        connection?.flush()
    }

    fun start(): ChannelFuture {
        val future = bootstrap.connect(ip, port)
        connection = future.channel()
        return future
    }

    fun close(): ChannelFuture {
        return connection?.disconnect() ?: throw Exception("Tried disconnecting while not connected")
    }
}