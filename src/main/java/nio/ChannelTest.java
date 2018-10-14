package nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {
	
	public static void main( String[] args ) throws Exception
	{
	    RandomAccessFile aFile = new RandomAccessFile("E:\\runyi\\22.txt", "rw");
	    FileChannel inChannel = aFile.getChannel();

	    ByteBuffer buf = ByteBuffer.allocate(8);
	    /**
	     * 使用 NIO Buffer 的步骤如下:

			将数据写入到 Buffer 中.
			
			调用 Buffer.flip()方法, 将 NIO Buffer 转换为读模式.
			
			从 Buffer 中读取数据
			
			调用 Buffer.clear() 或 Buffer.compact()方法, 将 Buffer 转换为写模式.
	     */

//	    int bytesRead = inChannel.read(buf);
//	    while (bytesRead != -1) {
//	        //buf.flip();//这个读必须要flip，设置position为0，然后才能读取buffer的内容
//	        while(buf.hasRemaining()){
//	            System.out.print((char) buf.get());
//	        }
//
//	        buf.clear();
//	        bytesRead = inChannel.read(buf);
//	    }
	    
	    //下面是写入
	    //如果写入的内容过多需要批量多次
	    //如果是追加的话，一开始设置一下channel的position，然后write会自动更position。
	    String newData = "New String to write to file..." + System.currentTimeMillis();
	    int len = newData.length();
	    int size = len/8;
	    int remain  = len%8;
	    inChannel.position(inChannel.size());
	   	for(int i=0; i<size; i++){
	   		buf.clear();
	   		System.out.println(i);
		    buf.put(newData.substring(i*8, i*8+8).getBytes());

		    buf.flip();
		    while(buf.hasRemaining()) {
		        inChannel.write(buf);
		    }
	   	}
	   

	   	buf.clear();
	    buf.put(newData.substring(size*8, size*8+remain).getBytes());

	    buf.flip();
	    while(buf.hasRemaining()) {
	        inChannel.write(buf);
	    }
	   
	    aFile.close();
	}

}
