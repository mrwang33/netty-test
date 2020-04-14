package top.lyq211;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) throws InterruptedException {
    new ServerBootstrap().group(new NioEventLoopGroup())
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              private String content = "";
              private ChannelHandlerContext channelHandlerContext = null;

              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) {
                channelHandlerContext = ctx;
                new Bootstrap().group(ctx.channel().eventLoop())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                      @Override
                      protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                          @Override
                          public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
                          }

                          @Override
                          public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            System.out.println("Received data");
                            content = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
                          }

                          @Override
                          public void channelReadComplete(ChannelHandlerContext ctx) {
                            System.out.println(content);
                            channelHandlerContext
                                .writeAndFlush(Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
                          }
                        });
                      }
                    }).connect("127.0.0.1", 9990)
                    .addListener((ChannelFutureListener) future -> {
                      if (future.isSuccess()) {
                        System.out.println("connect main server success!");
                      }
                    });
              }
            });
          }
        }).bind(9999).sync().addListener((ChannelFutureListener) future -> {
      if (future.isSuccess()) {
        System.out.println("server is ready !");
      }
    });
  }

}
