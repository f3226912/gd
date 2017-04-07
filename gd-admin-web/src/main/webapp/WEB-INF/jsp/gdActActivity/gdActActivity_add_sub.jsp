<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style type="text/css">
div#top{;width:100%;height:50px;border:1px solid grey;background-color:#F0F0F0;}
div#top1{;width:100%;height:100px;border:1px solid grey;background-color:#F0F0F0;}
div#left{;width:400px;height:200px;border:1px solid grey;float:left;}
div#middle{width:100px;height:200px;float:left;padding:30px;padding-top:60px;}
div#right{width:200px;height:200px;border:1px solid grey;float:right;}

.tab{border-top:1px solid #000;border-left:1px solid #000;text-align:center}
.tab td{border-bottom:1px solid #000;border-right:1px solid #000;}
.tab th{border-bottom:1px solid #000;border-right:1px solid #000;}
.sub_buyer div {padding-bottom: 15px;}
</style>
<div id="sub_buyer" class="sub_buyer" style="margin: 10px;">
	<form id="sub_order" action="">
		<div>
			<span style="color: red">*</span>支付渠道：
			<select id="payChannel" name="payChannel">
				<option value="">无数据</option>
			</select>
		</div>
		<div>
			卡类型：
			<select id="cardType" name="cardType">
				<option value="0">请选择</option>
				<option value="2">借记卡</option>
				<option value="1">贷记卡</option>
			</select>
		</div>
		<div>
			业务类型：
			<select id="busiType1" name="busiType1">
				<option value="0">请选择</option>
				<option value="1">本行</option>
				<option value="2">跨行</option>
			</select>
			<!-- <select id="busiType2" name="busiType2">
				<option value="0">请选择</option>
				<option value="1">同城</option>
				<option value="2">异地</option>
			</select> -->
			<input type="hidden" value="0" name="busiType2" />
		</div>
		<div >
		<b><span style="color:red;">*</span>刷卡补贴金额设置：</b>
		</div>
		<div>
		<span style="color:red">说明：刷卡手续费=银行实际扣缴的手续费</span>
		</div>
		<div>
		<span><input type="radio" id="proportionRadio" name="sub_radio" value="4" />按刷卡手续费比例：
		<input type="text" id="proportion" name="proportion" style="width: 60px;"  />%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;补贴上限：
		<input type="text" id="orderLimit" name="orderLimit" style="width: 60px;" /><span style="color:red">注:不填为不限</span>
		</span>
		</div>
		
		<div>
		<span><input type="radio" id="orderAmtCertRadio" name="sub_radio" value="3"  checked="checked" />按商品小计金额所属区间发放：</span>
		</div>
		<div style="color:red;">
			说明：取最小值作为补贴的基数 <br/>1、配置补贴最大值＞银行传输值(银行实际扣缴的手续费)<br/>2、配置补贴最大值＜银行传输值，取配置补贴的值
		</div>
		<!-- <div>
			<input type="radio" name="sub_radio" id="orderRadio" checked value="1" />按固定金额发放：<input type="text" id="orderAmt" name="orderAmt" />&nbsp;元/商品ID
		</div>
		<div>
			<input type="radio" name="sub_radio" value="2" id="orderCertRadio" />按订单商品小计金额百分比发放：<input type="text" id="orderSub" name="orderSub" />&nbsp;%，补贴上限&nbsp;<input type="text" id="orderLimit" name="orderLimit" />&nbsp;元/商品ID
		</div> -->
		<div style="backgroud-color:#e6faff;">
			<!-- <input type="radio" name="sub_radio" value="3" id="orderAmtCertRadio" /> -->
			<div id="sub_order_layout">
				<input type="hidden" value="0" name="hid_id" id="hid_id" />
				<div id="template_id" style="background-color:#f2f2f2; line-height:30px; padding:0px; padding-left: 10px;">
					<input type="text" id="startAmt" name="startAmt_id" style="width: 60px;" disabled="disabled"  />元~<input type="text" disabled="disabled" id="endAmt" name="endAmt_id" style="width: 60px;" />元&nbsp;
					<input type="radio" disabled="disabled" name="sub_lim_radio_id" id="orderCertRadio" checked value="0" onclick="emptyContent('_id',1)"  />按比例<input type="text" disabled="disabled" size=5 maxlength="5" id="propAmt" name="subPerc_id" />%补贴
					&nbsp;&nbsp;&nbsp;&nbsp;补贴上限<input  type="text" disabled="disabled" id="limitAmt" name="subLimit_id" style="width: 60px;" />元/商品ID
					<input type="radio" disabled="disabled" name="sub_lim_radio_id" value="1" id="orderFixedRadio" onclick="emptyContent('_id',2)" />按固定金额<input type="text" disabled="disabled" id="fixedAmt" name="subAmt_id" style="width: 60px;"  />元/商品ID
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a id="del_a_id" href="javascript:delRul('sub_order_layout', _id);" style="display:none;">&gt;&gt;删除</a>
				</div>
			</div>
			<div style="padding: 8px;">
				<a href="javascript:addMoreRuleForLayouter('sub_order_layout');">&gt;&gt;增加更多补贴发送区间</a>
			</div>
		</div>
		<div style="display:none" >
			<input type="checkbox" name="solderSubCheck" />&nbsp;单个卖家补贴上限<input type="text" name="solderSubAmt" id='solderSubAmt' />元
		</div>
	</form>
</div>

<script>
$(function(){

	$("#orderAmtCertRadio").click(function(){
		$("#startAmt").removeAttr("disabled");
		$("#endAmt").removeAttr("disabled");
		$("#orderCertRadio").removeAttr("disabled");

		if($("#orderCertRadio").attr("checked")=="checked"){
		$("#limitAmt").removeAttr("disabled");
		$("#propAmt").removeAttr("disabled");
		}else{
			$("#fixedAmt").removeAttr("disabled");
		}
		$("#orderFixedRadio").removeAttr("disabled");
		$("#proportion").attr("disabled","disabled");
		$("#orderLimit").attr("disabled","disabled");
 	});
	$("#proportionRadio").click(function(){
		$("#startAmt").attr("disabled","disabled");
		$("#endAmt").attr("disabled","disabled");
		$("#orderCertRadio").attr("disabled","disabled");
		$("#limitAmt").attr("disabled","disabled");
		$("#propAmt").attr("disabled","disabled");
		$("#orderFixedRadio").attr("disabled","disabled");
		$("#fixedAmt").attr("disabled","disabled");
		$("#proportion").removeAttr("disabled");
		$("#orderLimit").removeAttr("disabled");
 	});
})
	function emptyContent(id,type){
		if(type==1) {
			$("#template" + id + " #fixedAmt").val("");
			$("#template" + id + " #fixedAmt").attr("disabled", true);
			$("#template" + id + " #propAmt").removeAttr("disabled");
			$("#template" + id + " #limitAmt").removeAttr("disabled");
		} else {
			$("#template" + id + " #propAmt").val("");
			$("#template" + id + " #limitAmt").val("");
			$("#template" + id + " #propAmt").attr("disabled", true);
			$("#template" + id + " #limitAmt").attr("disabled", true);
			$("#template" + id + " #fixedAmt").removeAttr("disabled");
		}
	}
</script>
