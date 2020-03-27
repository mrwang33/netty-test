package top.lyq211;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) throws InterruptedException {
    new ServerBootstrap().group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<Channel>() {
              @Override
              protected void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpRequestDecoder());
                // Uncomment the following line if you don't want to handle HttpChunks.
                // p.addLast(new HttpObjectAggregator(1048576));
                pipeline.addLast(new HttpResponseEncoder());
                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                  @Override
                  public void channelRead(ChannelHandlerContext ctx, Object msg) {
                    HttpObject request = (HttpObject) msg;
                    System.out.println("get this: " + request.toString());
                    ByteBuf byteBuf = Unpooled.copiedBuffer("mata river", CharsetUtil.UTF_8);
                    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                  }

                  @Override
                  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                    ctx.close();
                    cause.printStackTrace();
                  }
                });
              }
            }).bind(9003).sync().addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
          System.out.println("server started ! ");
        }
      }
    });
  }
}
