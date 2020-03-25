package top.lyq211.launcher;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author wanghuan
 */
public class Client {

  public static void main(String[] args) {
    ChannelFuture future = new Bootstrap().group(new NioEventLoopGroup())
        .channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
          protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                String string = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
                System.out.println("server response: " + string);
              }
            });
          }
        }).connect("localhost", 9999);
    future.addListener(new ChannelFutureListener() {
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          System.out.println("成功连接");
          future.channel().writeAndFlush(Unpooled.copiedBuffer("hello server", CharsetUtil.UTF_8));
        } else {
          System.out.println("失败");
        }
      }
    });
  }
}
