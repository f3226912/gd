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
		<title>供应商订单信息_谷登农批网</title>
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
	</head>
<body>
	<table id="orderdg" title="">
	</table>
	<div id="ordertb" style="padding:5px;height:auto">
		<form id="orderSearchForm" method="post">
		<div>
			<input type="hidden" id="typeList" name="type" value="${type}" >
			<table>
			<tr><td>订单编号 :</td>
			<td><input type="text" id="orderNoList" class="easyui-validatebox" name="orderNo" style="width:120px" ></td>
			<td>所属市场 :</td>
			<td><select name="market" id="marketList" style="width: 120px;">
				<option value="">---请选择---</option>
			<c:forEach items="${allMarket2 }" var="market">
				<option value="${market.id }">${market.marketName }</option>
			</c:forEach>
			</select></td>
			<td>支付方式 :</td>
			<td><select name="payType" id="payTypeList" style="width: 120px;">
				<option value="">---请选择---</option>
				<option value="1">账户余额</option>
				<option value="2">POS刷卡</option>
				<option value="3">现金</option>
				<option value="12">账户余额+POS刷卡</option>
				<option value="13">账户余额+现金</option>
			</select></td>
			<c:if test="${type == '0'}">
			<td>订单状态 :</td>
			<td><select name="orderStatus" id="orderStatusList" style="width: 120px;">
					<option value="">---请选择---</option>
					<option value="1">待付款</option>
					<option value="11">待发货</option>
					<option value="12">待收货</option>
					<option value="3">已完成</option>
					<option value="8">已关闭</option>
					<!-- <option value="9">已过期</option> -->
				</select></td>	</c:if>
			</tr>
			<tr><td>用户账号 :</td>
			<td><input type="text" id="accountList" class="easyui-validatebox" name="account" style="width:120px" ></td>
			<td>手机号码 :</td>
			<td><input type="text" id="mobile" class="easyui-validatebox" name="mobile" style="width:120px" ></td>
			<td>商铺名称 :</td>
			<td><input type="text" id="shopNameList" class="easyui-validatebox" name="shopName" style="width:120px" ></td>
			<td>订单金额 :</td>
			
			<td><input type="text" id="orderAmountList" class="easyui-validatebox" name="orderAmount" style="width:120px" ></td></tr>
			<tr>
			<c:if test="${type == '0' || type == '3'}">
			<td>交易流水号:</td>
			<td><input type="text" id="statementIdList" class="easyui-validatebox" name="statementId" style="width:120px" ></td>
			<td>POS终端号:</td>
			<td><input type="text" id="posNumberList" class="easyui-validatebox" name="posNumber" style="width:120px" ></td>
			</c:if>
			<td>创建时间 :</td>
			<td <c:choose>  
   							<c:when test="${type == '0' || type == '3'}">   
   							   colspan="4"
   							</c:when>  
   							<c:otherwise> 
   							   colspan="7"
  							</c:otherwise>  
				</c:choose>   ><input  type="text"  id="startDate" name="startDate"
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:120px" >~
			<input  type="text"    id="endDate" name="endDate"
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:120px">
			
			<gd:btn btncode='BTNDDGLQBDD04'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='icon-reload'>重置</a></td>
			
			</tr>
			</table>
			
			
			
			
			
			
			
				
				
		
			
			
			
			
			
			
			
			
			
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
	return "<gd:btn btncode='BTNDDGLQBDD02'><a class='operate' href='javascript:;' onclick=editObj('"+row.persaleId+"');>详情</a>&nbsp;</gd:btn>";
}

var disableExport = false ;
</script>
<script type="text/javascript" src="${CONTEXT}js/order/supporder.js"></script>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
</html>