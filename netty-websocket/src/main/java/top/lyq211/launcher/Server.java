package top.lyq211.launcher;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.lyq211.handler.HttpRequestHandler;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) {
    NioEventLoopGroup loopGroup = new NioEventLoopGroup();
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(loopGroup).channel(NioServerSocketChannel.class).childHandler(
        new ChannelInitializer<Channel>() {
          protected void initChannel(Channel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpRequestHandler());
          }
        });
    ChannelFuture bind = serverBootstrap.bind(9999);
    ChannelFuture channelFuture = bind.syncUninterruptibly();
    Channel channel = channelFuture.channel();
  }
}
