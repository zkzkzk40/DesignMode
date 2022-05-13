package cn.edu.zjut.exception;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/13 19:05
 * @Description: 该类用于 异常
 */
public class FilePathNullException extends Exception{
    String message;

    public FilePathNullException(String message) {
        super(message);
        this.message = message;
    }
}
