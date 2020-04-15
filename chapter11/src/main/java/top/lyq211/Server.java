package top.lyq211;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.lyq211.handler.HttpPipelineInitializer;

/**
 * http服务器简单实现
 *
 * @author wanghuan
 */
public class Server {

  public static void main(String[] args) {
    new ServerBootstrap().group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
        .childHandler(new HttpPipelineInitializer())
        .bind(8085).syncUninterruptibly();
  }
}
