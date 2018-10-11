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

//	    int bytesRead = inChannel.read(buf);
//	    while (bytesRead != -1) {
//	        //buf.flip();
//	        while(buf.hasRemaining()){
//	            System.out.print((char) buf.get());
//	        }
//
//	        buf.clear();
//	        bytesRead = inChannel.read(buf);
//	    }
	    
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
