package top.lyq211;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup loopGroup = new NioEventLoopGroup();
    new ServerBootstrap().group(loopGroup).channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) {
                String string = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
                System.out.println(string);
              }
            });
          }
        }).bind(9999).sync().addListener((ChannelFutureListener) future -> {
      if (future.isSuccess()) {
        System.out.println("server started!");
      }
    });
  }
}
