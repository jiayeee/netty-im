package org.it.learn.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.it.learn.netty.common.constant.ConstantValue;
import org.it.learn.netty.common.model.Request;

import java.util.List;

/**
 * Request������
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
public class RequestDecoder extends ByteToMessageDecoder {

    // ���ݰ���������
    private static final int BASE_LENGTH = 4 + 2 + 2 + 4;

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

            // ��ֹ�ֽ�������
            if (in.readableBytes() > 2048) {
                // ��������֮�󣬺����յ��������п����ǲ������ģ��������滹��Ҫ����
                in.skipBytes(in.readableBytes());
            }

            int readerIndex;
            while (true) {
                readerIndex = in.readerIndex();

                if (in.readInt() == ConstantValue.FLAG) {
                    break;
                }

                // δ������ͷ��һ��һ���ֽ��Ұ�ͷ
                in.resetReaderIndex();
                in.readByte();

                if (in.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }

            short module = in.readShort();
            short cmd = in.readShort();
            int dataLength = in.readInt();

            if (dataLength > 0) {
                // ���ݰ�����������ԭ��ָ��
                if (in.readableBytes() < dataLength) {
                    in.readerIndex(readerIndex);
                    return;
                }

                byte[] bytes = new byte[dataLength];
                in.readBytes(bytes);

                Request request = new Request();
                request.setModule(module);
                request.setCmd(cmd);
                request.setData(bytes);

                out.add(request);
            }
        }
    }
}
