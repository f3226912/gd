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
		<meta http-equiv="keywords" content="农批网,公告信息,谷登农批网公告"/>
		<meta http-equiv="description" content="谷登农批网重大信息公告中心！"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>农批商订单信息_谷登农批网</title>
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
	</head>
<body>
	<table id="orderdg" title="">
	</table>
	<div id="ordertb" style="padding:5px;height:auto">
		<form id="orderSearchForm" method="post">
		<div>
			<input type="hidden" id="typeList" name="type" value="${type}" >
			订单编号 :
			<input type="text" id="orderNoList" class="easyui-validatebox" name="orderNo" style="width:120px" >
			所属市场 :
			<select name="market" id="marketList" style="width: 120px;">
				<option value="">---请选择---</option>
				<c:forEach items="${allMarket2 }" var="market">
					<option value="${market.id }">${market.marketName }</option>
				</c:forEach>
			</select>
			订单来源 :
			<select name="orderSource" id="orderSourceList" style="width: 120px;">
				<option value="">---请选择---</option>
				<option value="2">农商友APP</option>
				<option value="1">农批商APP</option>
				<option value="3">POS机</option>
				<option value="4">智能秤</option>
			</select>
			支付方式 :
			<select name="payType" id="payTypeList" style="width: 120px;">
				<option value="">---请选择---</option>
				<option value="1">账户余额</option>
				<option value="2">POS刷卡</option>
				<option value="3">现金</option>
				<option value="12">账户余额+POS刷卡</option>
				<option value="13">账户余额+现金</option>
			</select>
			<c:if test="${type == '0'}">
				订单状态 :
				<select name="orderStatus" id="orderStatusList" style="width: 120px;">
					<option value="">---请选择---</option>
					<option value="1">待付款</option>
					<option value="3">已付款</option>
					<option value="8">已关闭</option>
					<!-- <option value="9">已过期</option> -->
				</select>
			</c:if>
			活动状态 :
			<select name="isFirst" id="isFirstList" style="width: 120px;">
				<option value="">---请选择---</option>
				<option value="1">首单</option>
			</select>
			<br>
			买家账号 :
			<input type="text" id="accountList" class="easyui-validatebox" name="account" style="width:120px" >
			商铺名称 :
			<input type="text" id="shopNameList" class="easyui-validatebox" name="shopName" style="width:120px" >
			订单金额 :
			<input type="text" id="orderAmountList" class="easyui-validatebox" name="orderAmount" style="width:120px" >
<!-- 			审核状态: -->
<!-- 			<select name="examineStatus" id="examineStatusList" style="width: 150px;"> -->
<!-- 				<option value="">---请选择---</option> -->
<!-- 				<option value="0">待审核</option> -->
<!-- 				<option value="1">审核通过</option> -->
<!-- 				<option value="2">审核驳回</option> -->
<!-- 			</select> -->
			<!-- 银行流水号状态:
			<select name="upPayFlag" id="upPayFlagList" style="width: 150px;">
				<option value="">---请选择---</option>
				<option value="0">正常</option>
				<option value="1">异常</option>
			</select> -->
			<c:if test="${type == '0' || type == '3'}">
			<br>
			交易流水号:
			<input type="text" id="statementIdList" class="easyui-validatebox" name="statementId" style="width:120px" >
			POS终端号:
			<input type="text" id="posNumberList" class="easyui-validatebox" name="posNumber" style="width:120px" >
			</c:if>

			创建时间 :
			<input  type="text"  id="startDate" name="startDate"
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:120px" >~
			<input  type="text"    id="endDate" name="endDate"
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:120px">
			<gd:btn btncode='BTNDDGLQBDD04'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='icon-reload'>重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNDDGLQBDD03'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		<div id="editDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<div id="editButtondiv" style="display:inline-block">
		        	<gd:btn btncode='BTNDDGLQBDD06'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveObj('1')">提交</a></gd:btn>
<%-- 		        	<gd:btn btncode='BTNDDGLQBDD07'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveObj('2')">付款驳回</a></gd:btn> --%>
	        	</div>
<!-- 	        	<div id="updateButtondiv" style="display:inline-block"> -->
<%-- 					<gd:btn btncode='BTNDDGLQBDD08'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveObj('3')">修改流水号</a></gd:btn> --%>
<!-- 	        	</div> -->
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
	
	<!-- 编辑订单 -->
	<div id="editDialog2" class="easyui-dialog"  closed="true" modal="true">
		<div style="margin-left:20px;margin-top:20px;width:300px;">
		<span style="font-weight:bold;">订单信息</span><br>
		<table width="100%" border="0" class="comtable5">
			<tr>
				<td>订单编号:</td>
				<td><input name="orderNo" style="border-width:0"/></td>
			</tr>
			<tr>
				<td><span style="color:red">*</span>交易流水号:</td>
				<td><input name="statementId"/></td>
			</tr>
		</table>
		<input type="hidden" name="persaleId"/>
		</div>
		<div style="text-align:center;margin-top:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#editDialog2').dialog('close')">取消</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:editDialog2Save()">保存</a>
		</div>
	</div>
</body>
<script type="text/javascript">
function orderNoformat(value,row,index){
	var html="<gd:btn btncode='BTNDDGLQBDD01'><a class='operate' href='javascript:;' onclick=editObj('"+row.persaleId+"');>"+value+"</a>&nbsp;</gd:btn>";
	if(html==''){
		return value;
	}else{
		return html;
	}
}

function optformat(value,row,index){
	// 待付款，已关闭添加编辑按钮，2016/9/1 补单需求
	var edit = "";
	if(row.orderStatus =="1" || row.orderStatus =="8" || row.orderStatus =="9"){
		edit ="<gd:btn btncode='BTNDDGLQBDD09'><a class='operate' href='javascript:;' onclick=editObj2('"+row.persaleId+"','"+row.orderNo+"');>编辑</a>&nbsp;</gd:btn>";
	}
	edit = "<span style='width:30px;display:inline-block'>"+edit+"</span>";
	return edit+"<span style='width:30px;display:inline-block'><gd:btn btncode='BTNDDGLQBDD02'><a class='operate' href='javascript:;' onclick=editObj('"+row.persaleId+"');>详情</a>&nbsp;</gd:btn></span>";
}

var disableExport = false ;
</script>
<script type="text/javascript" src="${CONTEXT}js/order/order.js"></script>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
</html>