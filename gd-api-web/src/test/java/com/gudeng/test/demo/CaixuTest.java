package com.gudeng.test.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaixuTest {
	public static void main(String[] args) throws ParseException {
		Date activity = new SimpleDateFormat("yyyy-MM-dd").parse("2017-02-28");
		String nowDateTemp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date nowDate = new SimpleDateFormat("yyyy-MM-dd").parse(nowDateTemp);
		if (activity.getTime() >= nowDate.getTime()) {
            System.out.println("有优惠活动");
        } else if (activity.getTime() < nowDate.getTime()) {
            System.out.println("优惠活动已结束 ");
        }
	}
}
