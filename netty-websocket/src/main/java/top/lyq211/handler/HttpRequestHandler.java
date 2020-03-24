package top.lyq211.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author wanghuan
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


  protected void channelRead0(ChannelHandlerContext channelHandlerContext,
      FullHttpRequest fullHttpRequest) {
    // 处理 http 100
    if (HttpHeaders.is100ContinueExpected(fullHttpRequest)) {
      send100Continue(channelHandlerContext);
    }
    DefaultFullHttpResponse response = new DefaultFullHttpResponse(
        fullHttpRequest.getProtocolVersion(), HttpResponseStatus.OK);
    channelHandlerContext.writeAndFlush(response);
    channelHandlerContext.close();
  }

  private static void send100Continue(ChannelHandlerContext ctx) {
    FullHttpResponse response = new DefaultFullHttpResponse(
        HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
    ctx.writeAndFlush(response);
  }

}
