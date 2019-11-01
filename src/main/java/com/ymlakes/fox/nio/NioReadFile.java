package com.ymlakes.fox.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将channel中的数据写到buffer中
 * @author WeiYi
 */
public class NioReadFile {
    public static String pathname = "D:\\test\\src_test\\其他\\时间的刻刀.txt";



    public static void main(String[] args) {
        readNIO();
    }


    public static void readNIO() {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(new File(pathname));
            FileChannel channel = fin.getChannel();
            // 字节
            int capacity = 1000;
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            System.out.println("限制是：" + bf.limit() + ",容量是：" + bf.capacity() + " ,位置是：" + bf.position());
            int length = -1;
            while ((length = channel.read(bf)) != -1) {

                /*
                 * 注意，读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                 */
                System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" + bf.position());
                bf.clear();
                byte[] bytes = bf.array();
                System.out.println("start..............");

                String str = new String(bytes, 0, length);
                System.out.println(str);
                //System.out.write(bytes, 0, length);

                System.out.println("end................");

                System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" + bf.position());

            }


            channel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
