package top.lyq211;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Client {

  public static void main(String[] args) throws InterruptedException {
    new Bootstrap().group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(
        new ChannelInitializer<Channel>() {
          protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
              protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                System.out.println("server say: " + msg.toString(CharsetUtil.UTF_8));
              }

              @Override
              public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                System.out.println("channel 不再活动了 ");
              }

              @Override
              public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                System.out.println("channel 注销了");
              }
            });
          }
        }).connect("localhost", 9999).sync().addListener(new ChannelFutureListener() {
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          System.out.println("连接成功!");
        }
      }
    });
  }
}
