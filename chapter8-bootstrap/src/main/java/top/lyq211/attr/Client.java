package top.lyq211.attr;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Client {

  public static void main(String[] args) {
    AttributeKey<Object> id = AttributeKey.newInstance("id");
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.attr(id, "I want something just like this");
    bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(
        new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelActive(ChannelHandlerContext ctx) throws Exception {
                ctx.writeAndFlush(Unpooled
                    .copiedBuffer(ctx.channel().attr(id).get().toString(), CharsetUtil.UTF_8));
                super.channelActive(ctx);
              }

              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println(((ByteBuf) msg).toString(CharsetUtil.UTF_8));
              }
            });
          }
        }).option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .connect("127.0.0.1", 9990).syncUninterruptibly();
  }
}
