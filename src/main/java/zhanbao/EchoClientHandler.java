package zhanbao;

import java.io.PushbackInputStream;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class EchoClientHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger logger = Logger.getLogger(EchoClientHandler.class.getName());
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		//��ȡ�������˷��ص�����buf
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		//������˷��ص�byte����ת�����ַ���������ӡ������̨
		String body = new String(req, "UTF-8");
		System.out.println("receive data from server: " + body);
		
		
		
	}
	//�����쳣���ر�����
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
		logger.warning("Unexpected exception from downstream :" + cause.getMessage());
		ctx.close();
	}

}
