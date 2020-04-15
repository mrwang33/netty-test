import decoder.MessageToIntDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) {
    new ServerBootstrap().group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
        .childHandler(
            new ChannelInitializer<Channel>() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new MessageToIntDecoder());
                ch.pipeline().addLast(new SimpleChannelInboundHandler<Integer>() {
                  @Override
                  protected void channelRead0(ChannelHandlerContext ctx, Integer msg)
                      throws Exception {
                    System.out.println(msg);
                  }

                });
              }
            }).bind(9092).syncUninterruptibly();
  }
}
