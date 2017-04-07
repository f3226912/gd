package com.gudeng.commerce.gd.order.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sub_range_pay_rule")
public class SubRangePayRuleEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5814786509385474344L;
	
	private Long id;

    private Integer ruleId;

    private String lowerLimit;

    private String upperLimit;

    private String unit;

    private String subUnit;

    private Integer carType;

    private String truck;

    private Double subAmount;

    @Id
    @Column(name = "id")
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "ruleId")
    public Integer getRuleId(){

        return this.ruleId;
    }
    public void setRuleId(Integer ruleId){

        this.ruleId = ruleId;
    }
    @Column(name = "lowerLimit")
    public String getLowerLimit(){

        return this.lowerLimit;
    }
    public void setLowerLimit(String lowerLimit){

        this.lowerLimit = lowerLimit;
    }
    @Column(name = "upperLimit")
    public String getUpperLimit(){

        return this.upperLimit;
    }
    public void setUpperLimit(String upperLimit){

        this.upperLimit = upperLimit;
    }
    @Column(name = "unit")
    public String getUnit(){

        return this.unit;
    }
    public void setUnit(String unit){

        this.unit = unit;
    }
    @Column(name = "subUnit")
    public String getSubUnit(){

        return this.subUnit;
    }
    public void setSubUnit(String subUnit){

        this.subUnit = subUnit;
    }
    @Column(name = "carType")
    public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
    @Column(name = "truck")
    public String getTruck(){

        return this.truck;
    }

	public void setTruck(String truck){

        this.truck = truck;
    }
    @Column(name = "subAmount")
    public Double getSubAmount(){

        return this.subAmount;
    }
    public void setSubAmount(Double subAmount){

        this.subAmount = subAmount;
    }
}

