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
		//获取服务器端返回的数据buf
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		//将服务端返回的byte数组转换成字符串，并打印到控制台
		String body = new String(req, "UTF-8");
		System.out.println("receive data from server: " + body);
		
		
		
	}
	//发生异常，关闭连接
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
		logger.warning("Unexpected exception from downstream :" + cause.getMessage());
		ctx.close();
	}

}
