package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity(name = "en_post_log")
public class EnPostLogEntity implements java.io.Serializable {
//主键
	private     Long	  ID;   
	//接口版本号(填1.0) 
	private     String  version; 
	//字符集(填1) 1-UTF-8，不为空
	private     String charset ;    
	//POS终端号，不为空
	private     String machinenum  ; 
	//POS商户号，不为空
	private     String merchantnum  ;
	//1.请求原文 2.请求成功 3.请求失败(无对应数据)
	private     Integer state  ; 
	//交易类型（0—订单支付 1—刷卡消费）
	private     String  transype  ;   
	//谷登订单号（1—20），刷卡消费时为空
	private     Long orderno  ;    
	//订单金额（1—15），单位分
	private     String orderfee;     
	//手续费（1—15，单位分）
	private     String ratefee  ;    
	//支付金额（1—15，单位分）'
	private     String  payfee ;   
	//支付响应码，详情见附录'
	private     String  payresultcode;
	//支付响应码说明'
	private     String payresultmsg ;
	//支付卡号'
	private     String paycardno    ;
	//'交易日期'
	private     Date transdate  ;  
	//交易时间'
	private     Date transtime   ; 
	//'交易流水号（1—22）'
	private     String transseqno;   
	//'保留字节'
	private     String reserved  ;
	private Date createtime;
	//'备注'
    private     String bz  ;
	
	@Id
	@Column(name = "ID")
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	@Column(name = "version")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Column(name = "charset")
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	@Column(name = "machinenum")
	public String getMachinenum() {
		return machinenum;
	}
	public void setMachinenum(String machinenum) {
		this.machinenum = machinenum;
	}
	@Column(name = "merchantnum")
	public String getMerchantnum() {
		return merchantnum;
	}
	public void setMerchantnum(String merchantnum) {
		this.merchantnum = merchantnum;
	}
	@Column(name = "state")
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Column(name = "transype")
	public String getTransype() {
		return transype;
	}
	public void setTransype(String transype) {
		this.transype = transype;
	}
	@Column(name = "orderno")
	public Long getOrderno() {
		return orderno;
	}
	public void setOrderno(Long orderno) {
		this.orderno = orderno;
	}
	@Column(name = "orderfee")
	public String getOrderfee() {
		return orderfee;
	}
	public void setOrderfee(String orderfee) {
		this.orderfee = orderfee;
	}
	@Column(name = "ratefee")
	public String getRatefee() {
		return ratefee;
	}
	public void setRatefee(String ratefee) {
		this.ratefee = ratefee;
	}
	@Column(name = "payfee")
	public String getPayfee() {
		return payfee;
	}
	public void setPayfee(String payfee) {
		this.payfee = payfee;
	}
	@Column(name = "payresultcode")
	public String getPayresultcode() {
		return payresultcode;
	}
	public void setPayresultcode(String payresultcode) {
		this.payresultcode = payresultcode;
	}
	@Column(name = "payresultmsg")
	public String getPayresultmsg() {
		return payresultmsg;
	}
	public void setPayresultmsg(String payresultmsg) {
		this.payresultmsg = payresultmsg;
	}
	@Column(name = "paycardno")
	public String getPaycardno() {
		return paycardno;
	}
	public void setPaycardno(String paycardno) {
		this.paycardno = paycardno;
	}
	@Column(name = "transdate")
	public Date getTransdate() {
		return transdate;
	}
	public void setTransdate(Date transdate) {
		this.transdate = transdate;
	}
	@Column(name = "transtime")
	public Date getTranstime() {
		return transtime;
	}
	public void setTranstime(Date transtime) {
		this.transtime = transtime;
	}
	@Column(name = "transseqno")
	public String getTransseqno() {
		return transseqno;
	}
	public void setTransseqno(String transseqno) {
		this.transseqno = transseqno;
	}
	@Column(name = "reserved")
	public String getReserved() {
		return reserved;
	} 
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	@Column(name = "bz")
    public String getBz() {
    return bz;
	}
    public void setBz(String bz) {
    this.bz = bz;
    }
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}




}
