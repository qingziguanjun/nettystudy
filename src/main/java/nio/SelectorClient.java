package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class SelectorClient {

	//�ͻ���
    public void client() throws Exception {
        //1. ��ȡsocketChannel
        SocketChannel sChannel = SocketChannel.open();
        //2. ��������
        sChannel.connect(new InetSocketAddress("127.0.0.1", 9898));
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //3. ����ͨ��Ϊ������
        sChannel.configureBlocking(false);

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.nextLine();

            buf.put((new Date() + "��" + msg).getBytes());
            buf.flip();
            //4. ��ͨ��д����
            sChannel.write(buf);
            buf.clear();
            
		
        }
    }
    public static void main(String[] args) throws Exception {
		new SelectorClient().client();;
	}
   
}