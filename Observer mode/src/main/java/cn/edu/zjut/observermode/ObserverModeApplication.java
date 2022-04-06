package cn.edu.zjut.observermode;

import cn.edu.zjut.observermode.common.Stock;
import cn.edu.zjut.observermode.controller.WebSocketServer;
import cn.edu.zjut.observermode.observer.Impl.KlineDiagramObserver;
import cn.edu.zjut.observermode.observer.Impl.LineChartObserver;
import cn.edu.zjut.observermode.subject.Impl.StockExchangeCenter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static java.lang.Thread.sleep;
@EnableAsync
@SpringBootApplication
public class ObserverModeApplication {

    public ObserverModeApplication() {
        System.out.println("ObserverModeApplication构造函数");
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("WebSocketServer多线程启动");
                Random random=new Random();
                double price=random.nextDouble()*100+100;
                StockExchangeCenter stockExchangeCenter=new StockExchangeCenter();
                KlineDiagramObserver klineDiagramObserver=new KlineDiagramObserver(stockExchangeCenter);
                LineChartObserver lineChartObserver=new LineChartObserver(stockExchangeCenter);
                Calendar calendar=Calendar.getInstance();
                while(true){
                    calendar.add(Calendar.SECOND,random.nextInt(60*24*60/10));
                    //股票新成交价在90%~110%波动
                    price=price*(random.nextInt(20)+90)/100;
                    Stock stock=new Stock("茅台",price,calendar.getTime());
                    stockExchangeCenter.giveNewStock(stock);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
    public static void main(String[] args) {
        SpringApplication.run(ObserverModeApplication.class, args);
    }
}
