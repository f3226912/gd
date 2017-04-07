package com.gudeng.commerce.gd.home.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码工具类
 * 
 * @author lidong
 *
 */
public class RandUtil {

	/**
	 * 产生随机算术公式
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 * @author lidong
	 */
	public static Map<String, String> randArithmetic(int num1, int num2) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append(formatNum(num1));
		if (num1 * num2 == 0) {
			if (new Random().nextInt(5) % 2 == 0) {
				sb.append(" 乘 ");
			} else {
				sb.append(" × ");
			}
			map.put("result", "0");
		} else if (num1 % num2 == 0) {
			if (new Random().nextInt(5) % 2 == 0) {
				sb.append(" 除 ");
			} else {
				sb.append(" ÷ ");
			}
			map.put("result", "" + num1 / num2);
		} else if (num1 > num2) {
			if (new Random().nextInt(5) % 2 == 0) {
				sb.append(" 减 ");
			} else {
				sb.append(" - ");
			}
			map.put("result", "" + (num1 - num2));
		} else {
			if (new Random().nextInt(5) % 2 == 0) {
				sb.append(" 加 ");
			} else {
				sb.append(" + ");
			}
			map.put("result", "" + (num1 + num2));
		}
		sb.append(formatNum(num2));
//		if (new Random().nextInt(5) % 2 == 0) {
			sb.append(" = ? ");
//		} else {
//			sb.append(" 等于 ?");
//		}
		map.put("arithmetic", sb.toString());
		return map;
	}

	private static String formatNum(int num) {
		String numStr = null;
		int a = new Random().nextInt(3) % 3;
		switch (num) {
		case 0:
			if (a == 0) {
				numStr = " 0";
			} else {
				numStr = "零";
			}
			break;
		case 1:
			if (a == 0) {
				numStr = "一";
			} else if (a == 1) {
				numStr = "壹";
			} else {
				numStr = " 1";
			}
			break;
		case 2:
			if (a == 0) {
				numStr = "二";
			} else if (a == 1) {
				numStr = "贰";
			} else {
				numStr = " 2";
			}
			break;
		case 3:
			if (a == 0) {
				numStr = "三";
			} else if (a == 1) {
				numStr = "叁";
			} else {
				numStr = " 3";
			}
			break;
		case 4:
			if (a == 0) {
				numStr = "四";
			} else if (a == 1) {
				numStr = "肆";
			} else {
				numStr = " 4";
			}
			break;
		case 5:
			if (a == 0) {
				numStr = "五";
			} else if (a == 1) {
				numStr = "伍";
			} else {
				numStr = " 5";
			}
			break;
		case 6:
			if (a == 0) {
				numStr = "六";
			} else if (a == 1) {
				numStr = "陆";
			} else {
				numStr = " 6";
			}
			break;
		case 7:
			if (a == 0) {
				numStr = "七";
			} else if (a == 1) {
				numStr = "柒";
			} else {
				numStr = " 7";
			}
			break;
		case 8:
			if (a == 0) {
				numStr = "八";
			} else if (a == 1) {
				numStr = "捌";
			} else {
				numStr = " 8";
			}
			break;
		case 9:
			if (a == 0) {
				numStr = "九";
			} else if (a == 1) {
				numStr = "玖";
			} else {
				numStr = " 9";
			}
			break;

		default:
			numStr = "" + num;
			break;
		}

		return numStr;
	}

	public static void main(String[] args) {

		for (int i = 0; i < 20; i++) {

			Map<String, String> map = randArithmetic(new Random().nextInt(10), new Random().nextInt(10));
			System.out.println(map.get("arithmetic"));
			System.out.println(map.get("result"));
		}

	}
}
