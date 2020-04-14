package top.lyq211.launcher;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) {
    NioEventLoopGroup loopGroup = new NioEventLoopGroup();
    ChannelFuture channelFuture = new ServerBootstrap().group(loopGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<Channel>() {
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf in = (ByteBuf) msg;
                String string = in.toString(CharsetUtil.UTF_8);
                System.out.println("I got this : " + string);
                ByteBuf byteBuf = Unpooled.copiedBuffer("thx I got " + string, CharsetUtil.UTF_8);
                ctx.writeAndFlush(byteBuf);
              }
            });
          }
        }).bind(9990);
    channelFuture.syncUninterruptibly();
  }
}
