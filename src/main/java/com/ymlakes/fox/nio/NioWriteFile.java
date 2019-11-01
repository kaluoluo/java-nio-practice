package com.ymlakes.fox.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author WeiYi
 */
public class NioWriteFile {
    public static String filename = "d://write.txt";

    public static void main(String[] args) {
        writeNIO();
    }

    public static void writeNIO() {
        FileOutputStream fos = null;
        try {

            //由文件输出流拿到文件
            fos = new FileOutputStream(new File(filename));
            //建立通道
            FileChannel channel = fos.getChannel();
            System.out.println(channel.size());
            channel.truncate(10);
            //待写数据 一个utf8汉字占3字节，所以总共30字节
            ByteBuffer src = Charset.forName("utf8").encode("你好你好你好你好你好");
            // 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
            System.out.println("初始化容量和limit：" + src.capacity() + "," + src.limit());
            int length = 0;
            //channel.write把buffer内容写入到文件中
            //会出现文件空洞即写入的文件数据有缝隙
            while ((length = channel.write(src)) != 0) {
                /*
                 * 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
                 */
                System.out.println(channel.size());
                System.out.println("限制是：" + src.limit() + ",容量是：" + src.capacity() + " ,位置是：" + src.position());
                System.out.println("写入长度:" + length);
            }

            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
