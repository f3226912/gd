package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.AdAdvertise;

/**
 * @Description 广告管理
 * @Project gd-customer-intf
 * @ClassName AdAdvertiseDTO.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月13日 上午11:18:23
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class AdAdvertiseDTO extends AdAdvertise {

    private static final long serialVersionUID = 7010167104764215151L;
    /**
     * @Fields adspaceName 广告位名称
     */
    private String adspaceName;



    /**
     * @Fields adCanal 广告渠道
     * @since Ver 2.0
     */
    private String adCanal;
    /**
     * @Fields adType 广告类型
     * @since Ver 2.0
     */
    private String adType;
    private String marketName;

    private String startTimeStr;
    private String endTimeStr;

    private String spaceSign;
    private String replaceImg;
    /**
     * 产品类目1
     */
    private Long categoryId1;
    /**
     * 产品类目2
     */
    private Long categoryId2;
    /**
     * 产品类目3
     */
    private Long categoryId3;

    private String stateStr;
    
	private Double price;
	private String showPrice;
	private String unit;
	private Long businessId;

    private String menuId;
    
    private String stateArray;
    
	//是否参加活动，0：否，1 是
	private Integer promotion = 0;

	public Integer getPromotion() {
		return promotion;
	}
	public void setPromotion(Integer promotion) {
		this.promotion = promotion;
	}
    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getAdspaceName() {
        return adspaceName;
    }

    public void setAdspaceName(String adspaceName) {
        this.adspaceName = adspaceName;
    }

    public String getAdCanal() {
        return adCanal;
    }

    public void setAdCanal(String adCanal) {
        this.adCanal = adCanal;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getSpaceSign() {
        return spaceSign;
    }

    public void setSpaceSign(String spaceSign) {
        this.spaceSign = spaceSign;
    }

    public String getReplaceImg() {
        return replaceImg;
    }

    public void setReplaceImg(String replaceImg) {
        this.replaceImg = replaceImg;
    }

    public Long getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(Long categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Long getCategoryId3() {
        return categoryId3;
    }

    public void setCategoryId3(Long categoryId3) {
        this.categoryId3 = categoryId3;
    }

    // 状态(1正常2等待3到期4停用)
    public String getStateStr() {
        switch (getState()) {
        case "1":
            stateStr = "上架";
            break;
        case "2":
            stateStr = "等待";
            break;
        case "3":
            stateStr = "到期";
            break;
        case "4":
            stateStr = "下架";
            break;
        default:
            stateStr = "";
            break;
        }
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getShowPrice() {
		if(String.valueOf(price).equals("0.0")||String.valueOf(price).equals("0")){
			return "面议";
		}else{
			return String.valueOf(price)+"元/"+getUnit();
		}
		
	}

	public void setShowPrice() {
		this.showPrice = String.valueOf(price);
	}


	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

    public String getStateArray() {
        return stateArray;
    }

    public void setStateArray(String stateArray) {
        this.stateArray = stateArray;
    }
}
