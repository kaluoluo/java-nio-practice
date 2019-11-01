package com.ymlakes.fox.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioMethodTest {
    public static String pathname = "D:\\test\\src_test\\其他\\时间的刻刀.txt";
    public static String filename = "d://write.txt";
    public static String filename1 = "d://write1.txt";
    @Test
    public void testEqual(){
        ByteBuffer bf = ByteBuffer.allocate(48);
        ByteBuffer bf1 = ByteBuffer.allocate(48);
        bf.putChar('b');
        bf1.putChar('a');
        //true equals不比较buffer里面的元素
        System.out.println(bf.equals(bf1));
        System.out.println(bf.compareTo(bf1));
    }

    @Test
    public void testIORWFileSpendTime()throws Exception{
        long time = System.currentTimeMillis();

        FileInputStream fis = new FileInputStream(pathname);
        FileOutputStream fos = new FileOutputStream(filename);
        int num;
        //byte data[] = new byte[1024];
        while ((num = fis.read()) != -1) {
            fos.write(num);
        }
        fos.flush();
        fis.close();
        fos.close();
        System.out.println(System.currentTimeMillis()-time);
    }

    @Test
    public void testNIORWFileSpendTime()throws Exception{
        long time = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(pathname);
        FileChannel src = fis.getChannel();
        FileOutputStream fos = new FileOutputStream(filename1);
        FileChannel out = fos.getChannel();
        //src.transferTo(0,src.size(),out);
        int capacity = 1024;
        int length = -1;
        //int n = 0;
        ByteBuffer bf = ByteBuffer.allocate(capacity);
        while ((length = src.read(bf)) != -1) {
            bf.flip();
            out.write(bf);
            bf.clear();
        }
        src.close();
        out.close();
        fis.close();
        fos.close();
        System.out.println(System.currentTimeMillis()-time);
    }

}
