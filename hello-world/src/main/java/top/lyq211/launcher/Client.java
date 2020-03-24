package top.lyq211.launcher;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Client {

  public static void main(String[] args) {
    NioEventLoopGroup loopGroup = new NioEventLoopGroup();
    ChannelFuture channelFuture = new Bootstrap().group(loopGroup).channel(NioSocketChannel.class)
        .remoteAddress("localhost", 9999)
        .handler(new ChannelInitializer<Channel>() {
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelActive(ChannelHandlerContext ctx) throws Exception {
                ByteBuf byteBuf = Unpooled
                    .copiedBuffer("I want an apple", CharsetUtil.UTF_8);
                ctx.writeAndFlush(byteBuf);
              }

              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf byteBuf = (ByteBuf) msg;
                String string = byteBuf.toString(CharsetUtil.UTF_8);
                String x = "server response: " + string;
                System.out.println(x);
                ctx.writeAndFlush(Unpooled.copiedBuffer(x, CharsetUtil.UTF_8));
              }
            });
          }
        }).connect().syncUninterruptibly();
    channelFuture.syncUninterruptibly();
  }

}
