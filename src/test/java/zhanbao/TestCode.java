package zhanbao;

import java.util.ArrayList;
import java.util.LinkedList;


public class TestCode {

	public static void main(String[] args) throws Exception {
		String body = new String("add".getBytes(), "UTF-8");
		System.out.println("receive data from client:" + body);
		//ArrayList<E>
		//LinkedList<E>
		int[] a = {1,2};
		System.out.println(a[3]);
	}

}
