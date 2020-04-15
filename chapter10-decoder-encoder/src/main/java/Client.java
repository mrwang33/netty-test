import encoder.IntToMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wanghuan
 */
public class Client {

  public static void main(String[] args) {
    new Bootstrap().group(new NioEventLoopGroup()).channel(NioSocketChannel.class).handler(
        new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel ch) {
            ch.pipeline().addLast(new IntToMessageEncoder());
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelActive(ChannelHandlerContext ctx) {
                for (int i = 0; i < 10; i++) {
                  ctx.writeAndFlush(i);
                }
              }
            });
          }
        }).connect("127.0.0.1", 9092);
  }
}
