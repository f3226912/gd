package com.gudeng.commerce.gd.m.logic.utils;

public class RandomScoreGenerator {

	public static final int BASE_SCORE = 1000000;

	private static final double TOP_GRADE = 0.1;
	private static final double SECOND_GRADE = 0.15;

	public static final double THIRD_GRADE = 0.75;

	/**
	 * 获取积分
	 */
	public static int randomGenScore() {

		int target = (int) Math.abs(Math.random() * BASE_SCORE);
		int top = (int)(TOP_GRADE * BASE_SCORE);
		int second = (int)((SECOND_GRADE + TOP_GRADE) * BASE_SCORE);

		if (target > 0 && target <= top){
			return 3;
		}else if (target > top && target <= second){
			return 2;
		}else if (target > second && target <= BASE_SCORE){
			return 1;
		}
		return 1;
	}
	public static void main(String[] args) {
		int x = 0, y = 0, z = 0, index = 0, i = 0;
		int result = 0;
		while(i < 1000){
			index = 0 ; x = y = z = 0 ;
			while(index < 1000){
				result = randomGenScore();
				if (result == 1){
					x++;
				}else if(result == 2){
					y++;
				}else if(result == 3){
					z++;
				}
				index++;
			}
			i++;
			System.out.println("x : " + x + ", y : " + y + ", z : " + z);
		}
	}
}
