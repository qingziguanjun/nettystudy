package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class SelectorClient {

	//客户端
    public void client() throws Exception {
        //1. 获取socketChannel
        SocketChannel sChannel = SocketChannel.open();
        //2. 创建连接
        sChannel.connect(new InetSocketAddress("127.0.0.1", 9898));
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //3. 设置通道为非阻塞
        sChannel.configureBlocking(false);

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.nextLine();

            buf.put((new Date() + "：" + msg).getBytes());
            buf.flip();
            //4. 向通道写数据
            sChannel.write(buf);
            buf.clear();
            
		
        }
    }
    public static void main(String[] args) throws Exception {
		new SelectorClient().client();;
	}
   
}