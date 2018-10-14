package nio;

import java.nio.IntBuffer;

/**
 * 
 * @author sy
 *https://segmentfault.com/a/1190000006824155
 *�ο��������
 *buffer ��������
 *clear  positionΪ0��limitΪ��������������д
 *flip   limitΪд����position��Ȼ��positionΪ0�� ���Զ�д�Ĵ�0��д���λ�õ��������ݣ��п���û��д�������Բ��ǵ�����
 *
 *rewind ��������PostionΪ0����д��������
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