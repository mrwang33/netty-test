package top.lyq211;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import java.util.concurrent.TimeUnit;

/**
 * @author wanghuan
 */
public class Client {

  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup group = new NioEventLoopGroup();
    new Bootstrap().group(group).channel(NioSocketChannel.class)
        .handler(
            new ChannelInitializer<Channel>() {
              @Override
              protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                  @Override
                  public void channelActive(ChannelHandlerContext ctx) {
                    ctx.channel().eventLoop()
                        .scheduleAtFixedRate(() -> {
                              ctx.writeAndFlush(
                                  Unpooled.copiedBuffer("hello server!", CharsetUtil.UTF_8));
                              System.out.println("schedule");
                            }, 3, 3,
                            TimeUnit.SECONDS);
                  }
                });
              }
            }).connect("localhost", 9999).sync().addListener((ChannelFutureListener) future -> {
      if (future.isSuccess()) {
        System.out.println("connected");
      }
    });
  }
}
