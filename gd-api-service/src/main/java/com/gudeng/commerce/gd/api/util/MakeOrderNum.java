package com.gudeng.commerce.gd.api.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @ClassName: MakeOrderNum
 * @CreateTime 2015年9月13日 下午4:51:02
 * @author : mayi
 * @Description: 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 *
 */
public class MakeOrderNum {


    private static AtomicInteger counter = new AtomicInteger();

    /**
     * 长生消息id
     */
    public synchronized static long  getAtomicCounter() {
        if (counter.get() > 999999) {
            counter.set(1);
        }
        long time = System.currentTimeMillis();
        long returnValue = time * 100 + counter.incrementAndGet();
        return returnValue;
    }

    private synchronized static long incrementAndGet() {
        return counter.incrementAndGet();
    }

    public static void main(String[] args) {
    	
    	for (int i = 0; i < 100; i++) {
    		  System.out.println(MakeOrderNum.getAtomicCounter());
		}
         
      
    }
}
