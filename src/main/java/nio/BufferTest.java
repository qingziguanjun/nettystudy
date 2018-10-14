package nio;

import java.nio.IntBuffer;

/**
 * 
 * @author sy
 *https://segmentfault.com/a/1190000006824155
 *参考这个博文
 *buffer 三个方法
 *clear  position为0，limit为容量，可以重新写
 *flip   limit为写到的position，然后position为0， 可以读写的从0到写入的位置的所有数据，有可能没有写满，所以不是到容量
 *
 *rewind 仅仅是让Postion为0，读写都可以用
 */
public class BufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(2);
        intBuffer.put(1);
        intBuffer.put(2);
        System.err.println("position: " + intBuffer.position());

        intBuffer.rewind();
        System.err.println("position: " + intBuffer.position());
        intBuffer.put(3);
        intBuffer.put(4);
        System.err.println("position: " + intBuffer.position());

        
        intBuffer.flip();
        System.err.println("position: " + intBuffer.position());
        intBuffer.get();
        intBuffer.get();
        System.err.println("position: " + intBuffer.position());

        intBuffer.rewind();
        System.err.println("position: " + intBuffer.position());
        int a = intBuffer.get();
        System.err.println(a);
    }
}