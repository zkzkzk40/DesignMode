package cn.edu.zjut;

import java.util.concurrent.*;
/**
 * @Author: 张坤
 * @DateTime: 2022/4/27 13:54
 * @Description: 该类用于 测试
 */
public class UseLimitInstanceClass {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(10,10,1L, TimeUnit.SECONDS,new ArrayBlockingQueue(4), Executors.defaultThreadFactory());
        for(int i=0;i<10;i++){
            executorService.submit(new AccessLimitInstanceClassThread());
        }
        executorService.shutdown();
    }
}
