package zhanbao;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.unix.Buffer;

public class EchoServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
			throws Exception {
		//���տͻ��˷��������ݣ�ʹ��buf.readableBytes��ȡ���ݴ�С����ת����byte����
		//ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[msg.readableBytes()];
		msg.readBytes(req);
		//��byte����ת�����ַ������ڿ���̨��ӡ��
		String body = new String(req, "UTF-8");
		System.out.println("receive data from client:" + body);
		
		//�����յ��Ŀͻ��˷���������д���ͻ���
		ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
		ctx.write(resp);
		
	}
	
	//�����쳣���ر���·
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
		ctx.close();
	}
	//�����ͻ���������Ϣȫ��д��SocketChannel��
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
		ctx.flush();
	}

}
