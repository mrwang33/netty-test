package top.lyq211;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import top.lyq211.handler.HttpSnoopServerHandler;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) throws InterruptedException {
    new ServerBootstrap().group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<Channel>() {
              @Override
              protected void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpRequestDecoder());
                // Uncomment the following line if you don't want to handle HttpChunks.
                //p.addLast(new HttpObjectAggregator(1048576));
                pipeline.addLast(new HttpResponseEncoder());
                // Remove the following line if you don't want automatic content compression.
                //p.addLast(new HttpContentCompressor());
                pipeline.addLast(new HttpSnoopServerHandler());
              }
            }).bind(9003).sync().addListener((ChannelFutureListener) future -> {
      if (future.isSuccess()) {
        System.out.println("server started ! ");
      }
    });
  }
}
