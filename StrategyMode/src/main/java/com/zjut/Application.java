package com.zjut;

import com.zjut.common.MyFile;
import com.zjut.util.EncodingContext;
import com.zjut.util.encrypt.Impl.EncryptTypeEnum;
import com.zjut.util.io.FileOperation;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/23 20:00
 * @Description: 该类用于 实现
 */
public class Application {
    /**
     * 读取文件然后生成加密文件并保存
     * @author zk
     * @date 2022/3/24 16:16
 	 * @param path 文件路径
     */
    public static String readAndSave(String path,EncryptTypeEnum encryptTypeEnum){
        byte[] fileTXT= FileOperation.readFile2(path);
        MyFile myFile= new EncodingContext().encryptTxt(fileTXT, encryptTypeEnum);
        String newPath=path.substring(0,path.lastIndexOf(".")+1)+"zk";
        myFile.setFilePath(path);
        FileOperation.writeObject(newPath,myFile);
//        FileOperation.deleteFile(path);
        return newPath;
    }
    /**
     * 从文件路径中读取加密文件内容
     * @author zk
     * @date 2022/3/24 17:30
 	 * @param path
	 * @return com.zjut.common.MyFile
     */
    public static MyFile decryptFile(String path){
        if(!path.endsWith(".zk")){
            System.out.println(path+"非加密文件,不可解密");
            return null;
        }
        return FileOperation.readObject(path);
    }
    /**
     * 解密后写入到文件
     * @author zk
     * @date 2022/3/24 20:53
 	 * @param path 需要解密的文件,后缀需为.zk
     * @param newPath 被加密文件解密后存放路径
     */
    public static void decryptedFileWrite(String path,String newPath){
        MyFile myFile =decryptFile(path);
        FileOperation.writeBytes(new EncodingContext().decryptTxt(myFile),newPath);
    }
    /**
     * 重载 解密后写入到文件
     * @author zk
     * @date 2022/3/24 20:53
     * @param path 需要解密的文件,后缀需为.zk
     */
    public static void decryptedFileWrite(String path){
        MyFile myFile =decryptFile(path);
        FileOperation.writeBytes(new EncodingContext().decryptTxt(myFile),path);
    }
    public static void main(String[] args) {
        //1. 获取文件内容
        String path="D:\\desktop\\设计模式实验一 201906061726 张坤.docx";
        //2. 加密文件内容
        //3. 序列化保存文件
        String encryptedFilePath=readAndSave(path,EncryptTypeEnum.RSAEncrypt);
        String newPath="D:\\desktop\\2.docx";
        decryptedFileWrite(encryptedFilePath,newPath);
    }
}
