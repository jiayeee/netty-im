package org.it.learn.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.it.learn.netty.common.constant.ConstantValue;
import org.it.learn.netty.common.model.Response;

/**
 * Response编码器
 * 
 <pre>
 +----------+----------+----------+----------+-----------+-----------+
 |          |          |          |          |           |           |
 |  header  |   module |    cmd   |stateCode |dataLength |   data    |
 |          |          |          |          |           |           |
 +----------+----------+----------+----------+-----------+-----------+
 </pre>
 * 
 * 包头4字节
 * 模块号2字节
 * 命令号2字节
 * 状态码4字节
 * 数据长度4字节（描述数据部分）
 *
 * Created by lwz on 2017/06/21 16:03.
 */
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    /**
     * encode() 方法被调用时将会传入要被该类编码为 ByteBuf 的（类型为 I 的）出站消息。
     * 该 ByteBuf 随后将会被转发给 ChannelPipeline中的下一个 ChannelOutboundHandler
     
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        out.writeInt(ConstantValue.FLAG);
        out.writeShort(msg.getModule());
        out.writeShort(msg.getCmd());
        out.writeInt(msg.getStateCode());
        out.writeInt(msg.getDataLength());

        if (msg.getData() != null) {
            out.writeBytes(msg.getData());
        }
    }
}
