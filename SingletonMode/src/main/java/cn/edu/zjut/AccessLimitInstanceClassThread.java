package cn.edu.zjut;

import java.util.Random;
/**
 * @Author: 张坤
 * @DateTime: 2022/4/27 14:54
 * @Description: 该类用于 定义基础线程
 */
public class AccessLimitInstanceClassThread implements Runnable {
    @Override
	public void run() {
        LimitInstanceClass instance = LimitInstanceClass.getInstance();
        while (instance == null) {
            instance = LimitInstanceClass.getInstance();
        }
        instance.writeAccessMessage(Thread.currentThread().getName());
        try {
            Thread.sleep( new Random().nextInt(5) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        instance.printAccessMessage();
        instance.release();
    }
}
