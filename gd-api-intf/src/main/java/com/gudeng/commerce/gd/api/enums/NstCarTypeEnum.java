package com.gudeng.commerce.gd.api.enums;

public enum NstCarTypeEnum {
	
	OTHER_CAR(-1, "不限"),
	
	SMALL_CAR(1, "小型面包"),
	
	GOLDEN_CUP(2, "金杯"),
	
	SMALL_FLAT(3, "小型平板"),
	
	MID_FLAT(4, "中型平板"),
	
	SMALL_VAN(5, "小型厢货"),
	
	LARGE_VAN(6, "大型厢货"),
	
	GONODOLA_CAR(7, "敞车"),
	
	VAN(8, "厢式货车"),
	
	HIGH_SIDED_TRUCK(9, "高栏车"),
	
	FLAT_CAR(10, "平板车"),
	
	CONTAINER_CAR(11, "集装箱"),
	
	KEEY_WARM_CAR(12, "保温车"),
	
	FROZEN_CAR(13, "冷藏车"),
	
	LIVE_FRESH_CAR(14, "活鲜水车");

	private NstCarTypeEnum(int carType, String carTypeName) {
		this.carType = carType;
		this.carTypeName = carTypeName;
	}

	private final int carType;
	
	private final String carTypeName;
	
	public int getCarType() {
		return carType;
	}

	public String getCarTypeName() {
		return carTypeName;
	}
}
