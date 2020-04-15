package encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wanghuan
 */
public class IntToMessageEncoder extends MessageToByteEncoder<Integer> {

  @Override
  protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) {
    out.writeInt(msg);
  }
}
