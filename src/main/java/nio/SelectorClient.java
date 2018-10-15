package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class SelectorClient {

	// 服务端
	public void server() throws Exception {
		

		// 1. 获取socketChannel
		SocketChannel sChannel = SocketChannel.open();
		// 2. 创建连接
		sChannel.connect(new InetSocketAddress("127.0.0.1", 9898));
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 3. 设置通道为非阻塞
		sChannel.configureBlocking(false);

		// 3. 打开一个监听器
		Selector selector = Selector.open();
		// 4. 向监听器注册接收事件，
		//这里注册的为什么要是OP_READ，其他就无法通信
		sChannel.register(selector, SelectionKey.OP_READ);
	

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String msg = scanner.nextLine();
			//String msg="123";
			buf.put((new Date() + "：" + msg).getBytes());
			buf.flip();
			// 4. 向通道写数据
			sChannel.write(buf);
			buf.clear();
			System.out.println("123");
			while (selector.select() > 0) {
				// 5. 获取监听器上所有的监听事件值
				System.err.println("456");
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();

				// 6. 如果有值
				while (it.hasNext()) {
					// 7. 取到SelectionKey
					SelectionKey key = it.next();
					if (key.isReadable()) {
						// 10. 可读事件处理
						SocketChannel channel = (SocketChannel) key.channel();
						System.out.println("get" + channel.toString());
						readMsg(channel);

					}
					// 11. 移除当前key
					it.remove();
				}
				//这个break得加，不然select.select返回大于0了，一直是个死循环，所以消费完SelectKey之后要退出
				break;
			}
		}

	}

	private void readMsg(SocketChannel channel) throws IOException {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		int len = 0;
		while ((len = channel.read(buf)) > 0) {
			buf.flip();
			byte[] bytes = new byte[1024];
			buf.get(bytes, 0, len);
			System.out.println(new String(bytes, 0, len));
		}
	}

	// 客户端
	public void client() throws Exception {
		// 1. 获取socketChannel
		SocketChannel sChannel = SocketChannel.open();
		// 2. 创建连接
		sChannel.connect(new InetSocketAddress("127.0.0.1", 9898));
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 3. 设置通道为非阻塞
		sChannel.configureBlocking(false);

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String msg = scanner.nextLine();

			buf.put((new Date() + "：" + msg).getBytes());
			buf.flip();
			// 4. 向通道写数据
			sChannel.write(buf);
			buf.clear();

		}
	}

	public static void main(String[] args) throws Exception {
		new SelectorClient().server();
	}

}