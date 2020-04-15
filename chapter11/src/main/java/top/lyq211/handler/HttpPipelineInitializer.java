package top.lyq211.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

  @Override
  protected void initChannel(Channel ch) {
    ch.pipeline().addLast(new HttpRequestDecoder());
    ch.pipeline().addLast(new HttpResponseEncoder());
    ch.pipeline().addLast(new HttpServerCodec());
    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpObject>() {
      @Override
      protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        System.out.println(msg);
      }

      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("hello").addListener(ChannelFutureListener.CLOSE);
      }
    });
  }
}
