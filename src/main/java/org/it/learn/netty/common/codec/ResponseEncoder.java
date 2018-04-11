package org.it.learn.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.it.learn.netty.common.constant.ConstantValue;
import org.it.learn.netty.common.model.Response;

/**
 * Response������
 * 
 <pre>
 +----------+----------+----------+----------+-----------+-----------+
 |          |          |          |          |           |           |
 |  header  |   module |    cmd   |stateCode |dataLength |   data    |
 |          |          |          |          |           |           |
 +----------+----------+----------+----------+-----------+-----------+
 </pre>
 * 
 * ��ͷ4�ֽ�
 * ģ���2�ֽ�
 * �����2�ֽ�
 * ״̬��4�ֽ�
 * ���ݳ���4�ֽڣ��������ݲ��֣�
 *
 * Created by lwz on 2017/06/21 16:03.
 */
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    /**
     * encode() ����������ʱ���ᴫ��Ҫ���������Ϊ ByteBuf �ģ�����Ϊ I �ģ���վ��Ϣ��
     * �� ByteBuf ��󽫻ᱻת���� ChannelPipeline�е���һ�� ChannelOutboundHandler
     
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
