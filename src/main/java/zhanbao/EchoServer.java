package zhanbao;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoServer {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new EchoServer().bind(port);
	}
	public void bind(int port) throws Exception{
		//��������EventLoopGroupʵ��
		//EventLoopGroup�ǰ���һ��ר�����ڴ��������¼���NIO�߳���
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			//��������˸��������� ServerBootStrap����
			ServerBootstrap b = new ServerBootstrap();
			//����NIO�߳���
			b.group(bossGroup, workerGroup).
			//����NIOServerSocketChannel����Ӧ��JDK NIO��ServerSocketChannel
			channel(NioServerSocketChannel.class)
			//����TCP��������������������г���
			.option(ChannelOption.SO_BACKLOG, 1024)
			//����I/O�¼������࣬����������Ϣ�ı���뼰���ǵ�ҵ���߼�
			.childHandler(new ChannelInitializer<NioSocketChannel>() {

				@Override
				protected void initChannel(NioSocketChannel ch)
						throws Exception {
					ch.pipeline().addLast(new EchoServerHandler());
					
				}
			});
			//�󶨶˿ڣ�ͬ���ȴ��ɹ�
			ChannelFuture f = b.bind(port).sync();
			//�ȴ�����˼����˿ڹر�
			f.channel().closeFuture().sync();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�����˳����ͷ��̳߳���Դ
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			
		}
		
	}
}
