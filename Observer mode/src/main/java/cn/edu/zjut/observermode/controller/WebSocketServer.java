package cn.edu.zjut.observermode.controller;

import cn.edu.zjut.observermode.common.KStockAllData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 14:10
 * @Description: 该类用于 提供websocket服务
 */
@ServerEndpoint("/webSocket/{sid}/{type}")
@Component
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数,线程安全
     */
    private static AtomicInteger onlineNum = new AtomicInteger();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
     */
    private static ConcurrentHashMap<String, Session> KLineSessionPools = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Session> SimpleLineSessionPools = new ConcurrentHashMap<>();


    public static void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }
    public static void sendKlineInfo(KStockAllData kStockAllData){
        if(KLineSessionPools.size()==0){
            return;
        }
        JSONObject result=new JSONObject();
        result.put("xAxis",kStockAllData.getTime());
        List  data = new ArrayList();
        kStockAllData.getKStockAtomicDataVector().stream().forEach(item->{
            data.add(item.getAtomicData());
        });
        result.put("data",data);
        for (Session session: KLineSessionPools.values()) {
            try {
                sendMessage(session, String.valueOf(result));
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void sendSimplelineInfo(List<String> time,List<Double> price){
        if(SimpleLineSessionPools.size()==0){
            return;
        }
        JSONObject result=new JSONObject();
        result.put("xAxis",time);
        result.put("data",price);
        for (Session session: SimpleLineSessionPools.values()) {
            try {
                sendMessage(session, String.valueOf(result));
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }
    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName, @PathParam(value = "type") String type){
        if("candlestick".equals(type)){
            KLineSessionPools.put(userName, session);
        }else{
            SimpleLineSessionPools.put(userName,session);
        }
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用
     * @author zk
     * @date 2022/4/4 9:45
 	 * @param userName
     */
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName, @PathParam(value = "type") String type){
        if("candlestick".equals(type)){
            KLineSessionPools.remove(userName);
        }
        else{
            SimpleLineSessionPools.remove(userName);
        }
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException{
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session: KLineSessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
