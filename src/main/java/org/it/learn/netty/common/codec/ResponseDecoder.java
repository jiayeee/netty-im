package org.it.learn.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.it.learn.netty.common.constant.ConstantValue;
import org.it.learn.netty.common.model.Response;

import java.util.List;

/**
 * Response解码器
 *
 <pre>
 +----------+----------+----------+-----------+-----------+
 |          |          |          |           |           |
 |  header  |   module |    cmd   |dataLength |   data    |
 |          |          |          |           |           |
 +----------+----------+----------+-----------+-----------+
 </pre>
 *
 * 包头4字节
 * 模块号2字节
 * 命令号2字节
 * 数据长度4字节（描述数据部分）
 * 
 * Created by lwz on 2017/06/21 16:32.
 */
public class ResponseDecoder extends ByteToMessageDecoder {

    // 数据包基本长度
    private static final int BASE_LENGTH = 4 + 2 + 2 + 4 + 4;

    /**
     * decode() 方法被调用时将会传入一个包含了传入数据的 ByteBuf，以及一个用来添加解码消息的 List。
     * 
     * 对这个方法的调用将会重复进行，直到确定没有新的元素被添加到该 List，或者该 ByteBuf 中没有更多可读取的字节时为止。
     * 然后，如果该 List 不为空，那么它的内容将会被传递给ChannelPipeline 中的下一个 ChannelInboundHandler
     * 
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 可读长度必须大于基本长度
        if (in.readableBytes() >= BASE_LENGTH) {
            int readerIndex = in.readerIndex();

            while (true) {
                if (in.readInt() == ConstantValue.FLAG) {
                    break;
                }
            }

            short module = in.readShort();
            short cmd = in.readShort();
            int stateCode = in.readInt();
            int dataLength = in.readInt();

            if (dataLength > 0) {
                // 数据包不完整，还原读指针
                if (in.readableBytes() < dataLength) {
                    in.readerIndex(readerIndex);
                    return;
                }

                byte[] bytes = new byte[dataLength];
                in.readBytes(bytes);

                Response response = new Response();
                response.setModule(module);
                response.setCmd(cmd);
                response.setStateCode(stateCode);
                response.setData(bytes);

                out.add(response);
            }
        }
    }
}
