package zhanbao;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

	public static void main(String[] args) {
		int port = 8080;
		new EchoClient().connect(port, "127.0.0.1");
	}

	public void connect(int port, String host) {
		//�����ͻ��˴���I/O��д��NIO�߳���
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			//�����ͻ��˸���������
			Bootstrap b = new Bootstrap();
			b.group(group)
			//����NIoSocketChannel����Ӧ��JDK NIO���SocketChannel��
			.channel(NioSocketChannel.class)
			//����TCP����TCP_NODELAY
			.option(ChannelOption.TCP_NODELAY, true)
			//
			.handler(new ChannelInitializer<NioSocketChannel>() {

				@Override
				protected void initChannel(NioSocketChannel ch)
						throws Exception {
					ch.pipeline().addLast(new EchoClientHandler());
					
				}
			});
			
			//����һ�����Ӳ���
			ChannelFuture f = b.connect(host, port).sync();
			for(int i = 0; i< 1000;i++){
				//����ͻ��˷��͵�����ByteBuf����
				byte[] req = "hello,netty!".getBytes();
				ByteBuf messageBuffer = Unpooled.buffer(req.length);
				messageBuffer.writeBytes(req);
				
				//�����˷�������
				ChannelFuture channelFuture = f.channel().writeAndFlush(messageBuffer);
				channelFuture.syncUninterruptibly();
			}
			//�ȴ��ͻ�����·�ر�
			f.channel().closeFuture().sync();
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//���ŵ��˳�
			group.shutdownGracefully();
		}
		
	}

}
