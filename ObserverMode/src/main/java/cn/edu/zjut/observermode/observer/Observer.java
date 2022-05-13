package cn.edu.zjut.observermode.observer;

import cn.edu.zjut.observermode.common.Stock;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 14:17
 * @Description: 该类用于 实现观察者
 */
public interface Observer {
    /**
     * 观察者获取到信息后执行的内容
     * @author zk
     * @date 2022/4/3 14:38
     */
    public void getChangeInfo(Stock stock);
}
