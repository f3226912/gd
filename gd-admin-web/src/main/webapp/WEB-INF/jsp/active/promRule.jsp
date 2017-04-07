<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>活动</title>

	</head>
	
	
	<body>
		<style type="text/css">
	 		#sellFee input{
	 			text-align:right;
	 		}
	 		#buyFee input{
	 			text-align:right;
	 		}
		</style>
		<form id="addRuleFeeForm" method="post" action="${CONTEXT}active/addPromRuleFee">
		<div>
			<span style="font-size:16px;">活动基本规则</span>
			<br><hr>
			<div style="padding:2px;">
				<label>每家供应商参加活动的商品数量最多为：</label>
				<input type="text" id="maxProdNum" name="promRule.maxProdNum" style="width:80px;text-align:right;" class="easyui-validatebox" data-options="required:true,validType:['number','range[0,9999]']">
				<label>个</label>
			</div>
			<div style="padding:2px;">
				<label>向买家收取预付款：</label>
				<input type="text" id="prepayAmt" name="promRule.prepayAmt" style="width:100px;text-align:right;" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
				<label>元/单</label>
			</div>
		</div>
		<div>
			<span style="font-size:16px;">平台手续费收取规则</span>
			<br><hr>
			
			<div style="padding:2px;">
				<span>向买家收取手续费</span><br>
				<div style="border:1px solid #d3d3d3;padding:10px;" id="buyFee">
					<input type="hidden" name="promFees[0].feeSource" value="1">
					<label><input id="byeRaidioFirst" type="radio" name="promFees[0].feeType" value="1">按活动收取：</label>
					<input type="text" name="promFees[0].fee" style="width:60px" class=" easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>元/次</label>
					<label style="margin-left:50px;"><input type="radio" name="promFees[0].feeType" value="2">按订单收取：</label>
					<input type="text" name="promFees[0].fee" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>元/单</label>
					<br/>
					<label><input type="radio" name="promFees[0].feeType" value="3">按成交金额：</label>
					<input type="text" name="promFees[0].fee" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>%收取 (如：0.5%)</label>
					<label style="margin-left:50px;"><input type="radio" name="promFees[0].feeType" value="0">不收取手续费</label>
				</div>
				
			</div>
			<div style="padding:2px;">
				<span>向卖家收取手续费</span><br>
				<div style="border:1px solid #d3d3d3;padding:10px;" id="sellFee">
					<input type="hidden" name="promFees[1].feeSource" value="2">
					<label><input id="sellRaidioFirst" type="radio" name="promFees[1].feeType" value="1">按活动收取：</label>
					<input type="text" name="promFees[1].fee" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>元/次</label>
					<label style="margin-left:50px;"><input type="radio" name="promFees[1].feeType" value="2">按订单收取：</label>
					<input type="text" name="promFees[1].fee" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>元/单</label>
					<br/>
					<label><input type="radio" name="promFees[1].feeType" value="3">按成交金额：</label>
					<input type="text" name="promFees[1].fee" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>%收取 (如：0.5%)</label>
					<label style="margin-left:50px;"><input type="radio" name="promFees[1].feeType" value="0">不收取手续费</label>
					<br/>
					<label><input type="radio" name="promFees[1].feeType" value="4">成交金额：</label>
					<input type="text" name="promFees[1].orderPayAmt" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>万元以内，收取</label>
					<input type="text" name="promFees[1].fee" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99999999.99]']">
					<label>元，超出部分按</label>
					<input type="text" name="promFees[1].feeScale" style="width:60px" class="easyui-validatebox" data-options="required:true,validType:['floatPoint','range[0,99.99]']">
					<label>%收取（如：0.5%）</label>
				</div>
				
			</div>
		</div>
	</form>
	<div id="dlg-buttonsUpload" style="text-align:center;margin-top:10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addPromRuleFee()" id="icon-upload-btn">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" data-options="disabled:false" onclick="javascript:closeWindow(true)">关闭</a>
	</div>
	<script type="text/javascript" src="${CONTEXT}js/active/promRule.js?version=${version}"></script>
	</body>
	
</html>