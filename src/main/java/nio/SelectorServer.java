package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorServer {

	
    //服务端
    public void server() throws Exception {
        //1. 获取服务端通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.bind(new InetSocketAddress(9898));
        //2. 设置为非阻塞模式
        ssChannel.configureBlocking(false);
        
        //3. 打开一个监听器
        Selector selector = Selector.open();
        //4. 向监听器注册接收事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //注册的事件是Accept，当Accept发生时，select.select才不返回0？

        /**
         *  只有Selector.select()、Selector.select(long timeout)、Selector.selectNow()
         *  被调用并且操作系统内核是有事件通知才会将那些对这些事件感兴趣的SelectableChannel
         *  对应的SelectionKey放入selected-key集合，除此之外别无其它方法。
         *  
         *  只有直接调用selected-key集合的 Set.remove(Object o)或者通过Set.iterator()
         *  获取到的Iterator上调用Iterator.remove()来从selected-key集合删除某个SelectionKey，
         *  除此之外别其它方法。
			--------------------- 
			原文：https://blog.csdn.net/zilong_zilong/article/details/79588449?utm_source=copy 

         */
        while (selector.select() > 0) {
            //5. 获取监听器上所有的监听事件值
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            //6. 如果有值
            while (it.hasNext()) {
                    //7. 取到SelectionKey
                    SelectionKey key = it.next();

                //8. 根据key值判断对应的事件
                if (key.isAcceptable()) {
                    //9. 接入处理
                    SocketChannel socketChannel = ssChannel.accept();
                    socketChannel.configureBlocking(false);
                    System.out.println("accept" + socketChannel.toString());
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    //10. 可读事件处理
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("read " + channel.toString());
                    //如果把这句话注释了，不读channel中的内容，Client会不停的发送连接请求
                    //上面isAcceptable不停执行？为什么
                    readMsg(channel);
                    
                    
    				doWrite(channel);
                    
                }
                //11. 移除当前key
                it.remove();
            }
        }
    }
    
	private void doWrite(SocketChannel sc) throws IOException {
		byte[] bytes = "Hello,NonBlocking IO.".getBytes();
		//byte[] bytes = req.getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();
		sc.write(writeBuffer);
		
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