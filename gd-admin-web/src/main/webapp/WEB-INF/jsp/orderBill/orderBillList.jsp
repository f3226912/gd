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
		<title>cash</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="orderBilldg" title="">
	</table>
	<div id="orderBilltb" style="padding:5px;height:auto">
		<form id="orderBillSearchForm" method="post">
<!-- 			商户号 : <input  type="text" id=businessNo    class="easyui-validatebox"    name="businessNo" style="width:150px;display: none;" >
 -->			
 			所属市场：
			<select name="marketId" id="marketId">
				<option value="">全部</option>
				<c:forEach items="${marketList }" var="market">
				<option value="${market.id }">${market.marketName }</option>
				</c:forEach>
			</select>
 			终端号  : <input  type="text" id=clientNo    class="easyui-validatebox"    name="clientNo" style="width:150px" >
			系统参考号  : <input  type="text" id="sysRefeNo"    class="easyui-validatebox"    name="sysRefeNo" style="width:150px" >
			<div style="float: right;">
			交易总额：<input  type="text" id=tradeMoneySum    class="easyui-validatebox"   name="tradeMoneySum" readonly="readonly" >元
			</div></br>
			交易时间 :
			<input  type="text"  id="billBeginTime" name="billBeginTime"  
				onFocus="WdatePicker({onpicked:function(){billBeginTime.focus();},maxDate:'#F{$dp.$D(\'billEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){billBeginTime.focus();},maxDate:'#F{$dp.$D(\'billEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="billEndTime" name="billEndTime"   
				onFocus="WdatePicker({onpicked:function(){billBeginTime.focus();},minDate:'#F{$dp.$D(\'billBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){billBeginTime.focus();},minDate:'#F{$dp.$D(\'billBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			交易金额  : <input  type="text" id="tradeMoney"    class="easyui-validatebox"  data-options="validType:'floatPoint'"  name="tradeMoney" style="width:150px" >
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#orderBillSearchForm').form('validate');">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</form>
	</div>
	<div id="orderBillListDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#orderBillList">
		<div id="orderBillList" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#orderBillListDialog').dialog('close')">关闭</a>
	    </div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/orderbill/main.js"></script>
</html>