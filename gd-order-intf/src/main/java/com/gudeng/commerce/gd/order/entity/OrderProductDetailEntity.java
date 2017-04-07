package com.gudeng.commerce.gd.order.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_product_detail")
public class OrderProductDetailEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2784923454350448651L;

	private Integer preSalProductDetailId;

    private Long orderNo;

    private Integer productId;

    private String productName;

    private Double purQuantity;
    /**
     * 单位
     */
    private String unit;
    
    private Integer grossWeight; 	//  商品毛重

    private Double price;
    
    private Double price1;			//其他费用1
    
    private Double subTotal1;    //小计1
    
    private Double purQuantity1;	//小计1采购量
    
    private Double price2;			//其他费用2（小计2）
    
   
    
    private Double subTotal;//小计

    private Double tradingPrice;	//交易价

    private Integer ruleId; // 买家补贴规则ID
    
    private String subType; // 买家补贴类型 1按比例 2按每笔订单 3采购重量区间,4采购金额区间，5门岗目测审查
    
    private String subUnit; // 买家补贴单位 1元 51元/天/车

    private String subRule; // 买家补贴规则
    
    private String subTipInfo; 		// 买家补贴提示信息
    
    private Integer sellRuleId; 	// 卖家补贴规则ID
    
    private String sellSubType; 	// 卖家补贴类型 1按比例 2按每笔订单 3采购重量区间,4采购金额区间，5门岗目测审查
    
    private String sellSubUnit; 	// 卖家补贴单位 1元 51元/天/车

    private String sellSubRule; 	// 卖家补贴规则
    
    private String sellSubTipInfo; 	// 卖家补贴提示信息

    private Double subAmount; 		// 买家补贴额
    
    private Double sellSubAmount; 	// 卖家补贴额
    
    private Double suppSubAmount; 	// 供应商补贴额

    private Double needToPayAmount;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;
    
    private String remark; //备注
    
    private Integer hasActivity;
    /**
     * 是否出货 0未出货 1出货中 2已出货
     */
    private Integer hasDelivered;

    @Id
    @Column(name = "preSalProductDetailId")
    public Integer getPreSalProductDetailId(){

        return this.preSalProductDetailId;
    }
    public void setPreSalProductDetailId(Integer preSalProductDetailId){

        this.preSalProductDetailId = preSalProductDetailId;
    }
    @Column(name = "orderNo")
    public Long getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(Long orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "productId")
    public Integer getProductId(){

        return this.productId;
    }
    public void setProductId(Integer productId){

        this.productId = productId;
    }
    @Column(name = "productName")
    public String getProductName(){

        return this.productName;
    }
    public void setProductName(String productName){

        this.productName = productName;
    }
    @Column(name = "purQuantity")
    public Double getPurQuantity(){

        return this.purQuantity;
    }
    public void setPurQuantity(Double purQuantity){

        this.purQuantity = purQuantity;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "tradingPrice")
    public Double getTradingPrice(){

        return this.tradingPrice;
    }
    public void setTradingPrice(Double tradingPrice){

        this.tradingPrice = tradingPrice;
    }
    @Column(name = "ruleId")
    public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	@Column(name = "subRule")
	public String getSubRule() {
		return subRule;
	}
	public void setSubRule(String subRule) {
		this.subRule = subRule;
	}
    @Column(name = "subAmount")
    public Double getSubAmount(){

        return this.subAmount;
    }
    
	public void setSubAmount(Double subAmount){

        this.subAmount = subAmount;
    }
    @Column(name = "needToPayAmount")
    public Double getNeedToPayAmount(){

        return this.needToPayAmount;
    }
    public void setNeedToPayAmount(Double needToPayAmount){

        this.needToPayAmount = needToPayAmount;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    
    @Column(name = "unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	@Column(name = "sellSubAmount")
	public Double getSellSubAmount() {
		return sellSubAmount;
	}
	public void setSellSubAmount(Double sellSubAmount) {
		this.sellSubAmount = sellSubAmount;
	}
	
	@Column(name = "suppSubAmount")
	public Double getSuppSubAmount() {
		return suppSubAmount;
	}
	public void setSuppSubAmount(Double suppSubAmount) {
		this.suppSubAmount = suppSubAmount;
	}
	@Column(name = "subType")
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	@Column(name = "subUnit")
	public String getSubUnit() {
		return subUnit;
	}
	public void setSubUnit(String subUnit) {
		this.subUnit = subUnit;
	}
	@Column(name = "subTipInfo")
	public String getSubTipInfo() {
		return subTipInfo;
	}
	public void setSubTipInfo(String subTipInfo) {
		this.subTipInfo = subTipInfo;
	}
	@Column(name = "sellRuleId")
	public Integer getSellRuleId() {
		return sellRuleId;
	}
	public void setSellRuleId(Integer sellRuleId) {
		this.sellRuleId = sellRuleId;
	}
	@Column(name = "sellSubType")
	public String getSellSubType() {
		return sellSubType;
	}
	public void setSellSubType(String sellSubType) {
		this.sellSubType = sellSubType;
	}
	@Column(name = "sellSubUnit")
	public String getSellSubUnit() {
		return sellSubUnit;
	}
	public void setSellSubUnit(String sellSubUnit) {
		this.sellSubUnit = sellSubUnit;
	}
	@Column(name = "sellSubRule")
	public String getSellSubRule() {
		return sellSubRule;
	}
	public void setSellSubRule(String sellSubRule) {
		this.sellSubRule = sellSubRule;
	}
	@Column(name = "sellSubTipInfo")
	public String getSellSubTipInfo() {
		return sellSubTipInfo;
	}
	public void setSellSubTipInfo(String sellSubTipInfo) {
		this.sellSubTipInfo = sellSubTipInfo;
	}
	
	@Column(name = "hasDelivered")
	public Integer getHasDelivered() {
		return hasDelivered;
	}
	public void setHasDelivered(Integer hasDelivered) {
		this.hasDelivered = hasDelivered;
	}
	@Column(name="price1")
	public Double getPrice1() {
		return price1;
	}
	public void setPrice1(Double price1) {
		this.price1 = price1;
	}
	@Column(name="purQuantity1")
	public Double getPurQuantity1() {
		return purQuantity1;
	}
	public void setPurQuantity1(Double purQuantity1) {
		this.purQuantity1 = purQuantity1;
	}
	@Column(name="price2")
	public Double getPrice2() {
		return price2;
	}
	public void setPrice2(Double price2) {
		this.price2 = price2;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="subTotal1")
	public Double getSubTotal1() {
		return subTotal1;
	}
	public void setSubTotal1(Double subTotal1) {
		this.subTotal1 = subTotal1;
	}
	@Column(name="subTotal")
	public Double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	@Column(name="hasActivity")
	public Integer getHasActivity() {
		return hasActivity;
	}
	public void setHasActivity(Integer hasActivity) {
		this.hasActivity = hasActivity;
	}
	
	
}


