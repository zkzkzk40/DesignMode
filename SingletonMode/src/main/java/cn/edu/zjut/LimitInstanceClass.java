package cn.edu.zjut;

import java.util.Vector;
/**
 * @Author: 张坤
 * @DateTime: 2022/4/27 13:59
 * @Description: 该类用于 实现多例模式
 */
public class LimitInstanceClass {
    /**
     * 实例编号
     */
    private int id;
    /**
     * 输出信息
     */
	private String accessMessage;
    /**
     * 是否空闲
     */
	private Boolean isBusy;
    /**
     * 实例数上限
     */
    private static final int limitInstance = 3;
    /**
     * 存储对象实例的List表,vector向量线程安全
     */
    private static Vector<LimitInstanceClass> LimitInstanceClassList = new Vector<LimitInstanceClass>();

    private LimitInstanceClass() {
    }
    private LimitInstanceClass(int id) {
        this.id = id;
        this.isBusy = false;
    }
    /**
     * 实例化指定个数实例,线程安全
     * @author zk
     * @date 2022/4/27 14:22
     */
    private static synchronized void syncInit() {
        if (LimitInstanceClassList.isEmpty()) {
            for (int i = 0; i < limitInstance; i++) {
                LimitInstanceClassList.add(new LimitInstanceClass(i));
            }
        }
    }
    /**
     * 设置待输出的信息值
     * @author zk
     * @date 2022/4/27 14:23
 	 * @param message 待输出的信息
     */
    public void writeAccessMessage(String message) {
        this.accessMessage = message + " 正在调用实例" + id;
    }
    /**
     * 输出信息
     * @author zk
     * @date 2022/4/27 14:23
     */
    public void printAccessMessage() {
        System.out.print(accessMessage + "\n");
    }
    /**
     * 释放对象实例,即将对象设置为空闲
     * @author zk
     * @date 2022/4/27 14:24
     */
    public void release() {
        this.isBusy = false;
    }
    /**
     * 判断是否存在空闲的对象,线程安全
     * @author zk
     * @date 2022/4/27 14:24
	 * @return cn.edu.zjut.LimitInstanceClass
     */
    public static synchronized LimitInstanceClass getInstance() {
        if (LimitInstanceClassList.isEmpty()) {
			syncInit();
		}
        for (int i = 0; i < LimitInstanceClassList.size(); i++) {
            LimitInstanceClass instance = LimitInstanceClassList.get(i);
            if (!instance.isBusy) {
                instance.isBusy = true;
                return instance;
            }
        }
        return null;
    }
}
