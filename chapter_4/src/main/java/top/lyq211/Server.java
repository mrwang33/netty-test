package top.lyq211;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) throws InterruptedException {
//    NioEventLoopGroup group = new NioEventLoopGroup();
    // 只要将group与channel改掉就可以实现OIO传输
    OioEventLoopGroup group = new OioEventLoopGroup();
    Class<OioServerSocketChannel> channelClass = OioServerSocketChannel.class;
//    Class<NioServerSocketChannel> channelClass = NioServerSocketChannel.class;

    new ServerBootstrap().group(group).channel(channelClass).childHandler(
        new ChannelInitializer<Channel>() {
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelActive(ChannelHandlerContext ctx) {
                ByteBuf byteBuf = Unpooled.copiedBuffer("hello!", CharsetUtil.UTF_8);
                ctx.writeAndFlush(byteBuf).addListener((ChannelFutureListener) future -> {
                  if (future.isSuccess()) {
                    System.out.println("消息发送成功 bye bye");
                    future.channel().close();
                  }
                });
              }

              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                super.channelRead(ctx, msg);
              }
            });
          }
        }).bind("localhost", 9999).sync().addListener((ChannelFutureListener) future -> {
      if (future.isSuccess()) {
        System.out.println("服务器启动完成");
      }
    });
  }
}
