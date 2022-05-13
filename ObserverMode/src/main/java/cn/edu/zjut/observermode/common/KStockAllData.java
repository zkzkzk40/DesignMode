package cn.edu.zjut.observermode.common;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 21:17
 * @Description: 该类用于 K线图的总数据维护
 */
@Data
public class KStockAllData {
    /**
     * 时间格式化
     */
    static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    /**
     * K线图原子数据
     */
    Vector<KStockAtomicData> kStockAtomicDataVector;
    /**
     * 时间类存储
     */
    Vector<Date> dateVector ;
    public void addNewStock(Stock stock){
        if(!dateVector.isEmpty()&&isTheSameDay(stock.getDate(),dateVector.get(dateVector.size()-1))){
            //在同一天内
            kStockAtomicDataVector.get(kStockAtomicDataVector.size()-1).updateStock(stock);
        }
        else{
            //不在同一天
            kStockAtomicDataVector.add(new KStockAtomicData(stock));
            dateVector.add(stock.getDate());
        }
    }
    /**
     * 获取时间集合的函数
     * @author zk
     * @date 2022/4/6 19:23
	 * @return java.util.List<java.lang.String> 存储时间的List集合,需要转换
     */
    public List<String> getTime(){
        List<String> result=new ArrayList<>();
        dateVector.forEach(item->{
            result.add(simpleDateFormat.format(item));
        });
        return result;
    }

    public KStockAllData() {
        kStockAtomicDataVector =new Vector<>();
        dateVector=new Vector<>();
    }
    /**
     * 判断是否同一天
     * @author zk
     * @date 2022/4/6 19:23
 	 * @param d1
 	 * @param d2
	 * @return boolean
     */
    private static boolean isTheSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }
}
