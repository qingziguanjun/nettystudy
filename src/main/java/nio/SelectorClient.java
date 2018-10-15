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

	// �����
	public void server() throws Exception {
		

		// 1. ��ȡsocketChannel
		SocketChannel sChannel = SocketChannel.open();
		// 2. ��������
		sChannel.connect(new InetSocketAddress("127.0.0.1", 9898));
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 3. ����ͨ��Ϊ������
		sChannel.configureBlocking(false);

		// 3. ��һ��������
		Selector selector = Selector.open();
		// 4. �������ע������¼���
		//����ע���ΪʲôҪ��OP_READ���������޷�ͨ��
		sChannel.register(selector, SelectionKey.OP_READ);
	

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String msg = scanner.nextLine();
			//String msg="123";
			buf.put((new Date() + "��" + msg).getBytes());
			buf.flip();
			// 4. ��ͨ��д����
			sChannel.write(buf);
			buf.clear();
			System.out.println("123");
			while (selector.select() > 0) {
				// 5. ��ȡ�����������еļ����¼�ֵ
				System.err.println("456");
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();

				// 6. �����ֵ
				while (it.hasNext()) {
					// 7. ȡ��SelectionKey
					SelectionKey key = it.next();
					if (key.isReadable()) {
						// 10. �ɶ��¼�����
						SocketChannel channel = (SocketChannel) key.channel();
						System.out.println("get" + channel.toString());
						readMsg(channel);

					}
					// 11. �Ƴ���ǰkey
					it.remove();
				}
				//���break�üӣ���Ȼselect.select���ش���0�ˣ�һֱ�Ǹ���ѭ��������������SelectKey֮��Ҫ�˳�
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

	// �ͻ���
	public void client() throws Exception {
		// 1. ��ȡsocketChannel
		SocketChannel sChannel = SocketChannel.open();
		// 2. ��������
		sChannel.connect(new InetSocketAddress("127.0.0.1", 9898));
		ByteBuffer buf = ByteBuffer.allocate(1024);
		// 3. ����ͨ��Ϊ������
		sChannel.configureBlocking(false);

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			String msg = scanner.nextLine();

			buf.put((new Date() + "��" + msg).getBytes());
			buf.flip();
			// 4. ��ͨ��д����
			sChannel.write(buf);
			buf.clear();

		}
	}

	public static void main(String[] args) throws Exception {
		new SelectorClient().server();
	}

}