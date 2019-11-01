package com.ymlakes.fox.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioReadFileAndWriteFile {
    public static String pathname = "D:\\test\\src_test\\其他\\时间的刻刀.txt";

    public static void main(String[] args) {
        readAndWrite();
    }
    private static void readAndWrite(){
        RandomAccessFile aFile = null;
        try {

            aFile = new RandomAccessFile(pathname, "rw");
            FileChannel inChannel = aFile.getChannel();

            //create buffer with capacity of 48 bytes
            ByteBuffer buf = ByteBuffer.allocate(48);
            //read into buffer.
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                System.out.println("限制是：" + buf.limit() + ",容量是：" + buf.capacity() + " ,位置是：" + buf.position());
                //make buffer ready for read
                buf.flip();
                System.out.println("限制是：" + buf.limit() + ",容量是：" + buf.capacity() + " ,位置是：" + buf.position());
                while (buf.hasRemaining()) {
                    // read 1 byte at a time
                    System.out.print((char) buf.get(0)+buf.get());
                }

                //make buffer ready for writing
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
            inChannel.close();
        }catch (Exception e){
            try {
                aFile.close();
            }catch (Exception e1){

            }

        }

    }
}
