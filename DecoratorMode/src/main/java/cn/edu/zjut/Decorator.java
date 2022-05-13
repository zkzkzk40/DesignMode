package cn.edu.zjut;

import lombok.Data;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/13 12:41
 * @Description: 该类用于 装饰模式
 */
@Data
public abstract class Decorator extends ReplaceWriter{

    private ReplaceWriter replaceWriter;

    public Decorator() {
    }

    public Decorator(ReplaceWriter replaceWriter) {
        this.replaceWriter = replaceWriter;
    }
}
