package com.zjut.util.io;

import com.zjut.util.encrypt.Impl.EncryptTypeEnum;
import com.zjut.common.MyFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/24 10:00
 * @Description: 该类用于 文件写入读出
 */
public class FileOperation {
    /**
     * 删除文件
     * @author zk
     * @date 2022/3/24 16:56
 	 * @param path 文件路径
     */
    public static void deleteFile(String path){

        File file=new File(path);
        if (file.exists()){
            file.delete();
        }
    }
    /**
     * 将序列化的MyFile对象写入文件
     * @author zk
     * @date 2022/3/24 16:57
 	 * @param path
 	 * @param myFile
     */
    public static void writeObject(String path, MyFile myFile){
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(fileOutputStream);
            o.writeObject(myFile);
            fileOutputStream.close();
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取zk文件中的多个已被序列化写入的类 TODO 未完成
     * @author zk
     * @date 2022/3/24 17:43
 	 * @param path
	 * @return byte[]
     */
    public static byte[] readEncryptedFile(String path){
        byte[] result = new byte[0];
        MyFile myFile = null;
        File file = new File(path);
        if(file.exists()){
            ObjectInputStream ois;
            try {
                FileInputStream fn=new FileInputStream(file);
                ois = new ObjectInputStream(fn);
                myFile = (MyFile) ois.readObject();
                while(fn.available()>0){//代表文件还有内容
                    result= (byte[]) ois.readObject();//从流中读取对象
                }
                ois.close();//注意在循环外面关闭
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        System.out.println(new String(result));
        return result;
    }
    /**
     * 读取文件并反序列化生成类
     * @author zk
     * @date 2022/3/24 17:41
 	 * @param path
	 * @return MyFile
     */
    public static MyFile readObject(String path){
        MyFile myFile = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(path)));
            myFile = (MyFile) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return myFile;
    }

    /**
     * 读取文件 较高效 有问题!
     * @author zk
     * @date 2022/3/24 17:45
 	 * @param path
	 * @return byte[]
     */
    public static byte[] readFile(String path){
        FileChannel fc = null;
        byte[] result = new byte[0];

        try (FileChannel fileChannel = fc = new RandomAccessFile(path, "r").getChannel()) {
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,fc.size()).load();
            result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 从文件路径中读取文件
     * @author zk
     * @date 2022/3/24 17:45
     * @param path
     * @return byte[]
     */
    public static byte[] readFile2(String path){
        InputStream input = null;
        byte[] byt = null;
        try {
            File file = new File(path);
            input = new FileInputStream(file);
            byt = new byte[input.available()];
            input.read(byt);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return byt;
    }
    /**
     * 从文件中读取文件
     * @author zk
     * @date 2022/3/24 17:45
     * @param file
     * @return byte[]
     */
    public static byte[] readFile(File file){
        InputStream input = null;
        byte[] byt = null;
        try {
            input = new FileInputStream(file);
            byt = new byte[input.available()];
            input.read(byt);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return byt;
    }
    /**
     * 读取文件
     * @author zk
     * @date 2022/3/24 17:45
     * @param path
     * @return byte[]
     */
    public static byte[] readFile3(String path){
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    /**
     * 字节流的形式写入文件
     * @author zk
     * @date 2022/3/24 17:46
 	 * @param bytes
 	 * @param path
     */
    public static void writeBytes(byte[] bytes, String path){
        File file=new File(path);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
