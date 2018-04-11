package org.it.learn.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.it.learn.netty.common.constant.ConstantValue;
import org.it.learn.netty.common.model.Response;

import java.util.List;

/**
 * Response������
 *
 <pre>
 +----------+----------+----------+-----------+-----------+
 |          |          |          |           |           |
 |  header  |   module |    cmd   |dataLength |   data    |
 |          |          |          |           |           |
 +----------+----------+----------+-----------+-----------+
 </pre>
 *
 * ��ͷ4�ֽ�
 * ģ���2�ֽ�
 * �����2�ֽ�
 * ���ݳ���4�ֽڣ��������ݲ��֣�
 * 
 * Created by lwz on 2017/06/21 16:32.
 */
public class ResponseDecoder extends ByteToMessageDecoder {

    // ���ݰ���������
    private static final int BASE_LENGTH = 4 + 2 + 2 + 4 + 4;

    /**
     * decode() ����������ʱ���ᴫ��һ�������˴������ݵ� ByteBuf���Լ�һ��������ӽ�����Ϣ�� List��
     * 
     * ����������ĵ��ý����ظ����У�ֱ��ȷ��û���µ�Ԫ�ر���ӵ��� List�����߸� ByteBuf ��û�и���ɶ�ȡ���ֽ�ʱΪֹ��
     * Ȼ������� List ��Ϊ�գ���ô�������ݽ��ᱻ���ݸ�ChannelPipeline �е���һ�� ChannelInboundHandler
     * 
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // �ɶ����ȱ�����ڻ�������
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
                // ���ݰ�����������ԭ��ָ��
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
