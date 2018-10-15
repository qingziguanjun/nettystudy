package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class SelectorServer {

	
    //�����
    public void server() throws Exception {
        //1. ��ȡ�����ͨ��
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.bind(new InetSocketAddress(9898));
        //2. ����Ϊ������ģʽ
        ssChannel.configureBlocking(false);
        
        //3. ��һ��������
        Selector selector = Selector.open();
        //4. �������ע������¼�
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //ע����¼���Accept����Accept����ʱ��select.select�Ų�����0��

        /**
         *  ֻ��Selector.select()��Selector.select(long timeout)��Selector.selectNow()
         *  �����ò��Ҳ���ϵͳ�ں������¼�֪ͨ�ŻὫ��Щ����Щ�¼�����Ȥ��SelectableChannel
         *  ��Ӧ��SelectionKey����selected-key���ϣ�����֮���������������
         *  
         *  ֻ��ֱ�ӵ���selected-key���ϵ� Set.remove(Object o)����ͨ��Set.iterator()
         *  ��ȡ����Iterator�ϵ���Iterator.remove()����selected-key����ɾ��ĳ��SelectionKey��
         *  ����֮�������������
			--------------------- 
			ԭ�ģ�https://blog.csdn.net/zilong_zilong/article/details/79588449?utm_source=copy 

         */
        while (selector.select() > 0) {
            //5. ��ȡ�����������еļ����¼�ֵ
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            //6. �����ֵ
            while (it.hasNext()) {
                    //7. ȡ��SelectionKey
                    SelectionKey key = it.next();

                //8. ����keyֵ�ж϶�Ӧ���¼�
                if (key.isAcceptable()) {
                    //9. ���봦��
                    SocketChannel socketChannel = ssChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("accept" + socketChannel.toString());
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    //10. �ɶ��¼�����
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("read" + channel.toString());
                    readMsg(channel);
                    
                    //������д�ص�client��
        			ByteBuffer buffer = ByteBuffer.allocate(1024);
        			channel.read(buffer);
        			
        			buffer.flip();
        			channel.write(buffer);
                }
                //11. �Ƴ���ǰkey
                it.remove();
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
    public static void main(String[] args) throws Exception {
		new SelectorServer().server();

	}

}