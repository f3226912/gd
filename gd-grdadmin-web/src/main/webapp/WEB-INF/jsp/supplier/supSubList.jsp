<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
		<title>货主补贴</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="supSubdg" title="">
	</table>
	<div id="supSubtb" style="padding:5px;height:auto">
		<form id="supSubSearchForm" method="post">
		<div>
			<input type="hidden" id="type" name="type" value="${type }"/>
			入场时间:
			<input  type="text"  id="totalStartTime" name="totalStartTime"  
				onFocus="WdatePicker({onpicked:function(){totalStartTime.focus();},maxDate:'#F{$dp.$D(\'totalEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){totalStartTime.focus();},maxDate:'#F{$dp.$D(\'totalEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:150px" >~
			<input  type="text"    id="totalEndTime" name="totalEndTime"   
				onFocus="WdatePicker({onpicked:function(){totalEndTime.focus();},minDate:'#F{$dp.$D(\'totalStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){totalEndTime.focus();},minDate:'#F{$dp.$D(\'totalStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px">
			<br/>出场时间:
			<input  type="text"  id="tareStartTime" name="tareStartTime"  
				onFocus="WdatePicker({onpicked:function(){tareStartTime.focus();},maxDate:'#F{$dp.$D(\'tareEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){tareStartTime.focus();},maxDate:'#F{$dp.$D(\'tareEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:150px" >~
			<input  type="text"    id="tareEndTime" name="tareEndTime"   
				onFocus="WdatePicker({onpicked:function(){tareEndTime.focus();},minDate:'#F{$dp.$D(\'tareStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){tareEndTime.focus();},minDate:'#F{$dp.$D(\'tareStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px">
			
			<br/>供应商账号：<input type="text" id="account" class="easyui-validatebox" name="account" style="width:150px;" />
			入库单：<input type="text" id="inStoreNo" class="easyui-validatebox" name="inStoreNo" style="width:150px;" />
			<br/>
			<gd:btn btncode='BTNGYSDBT01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#supSubSearchForm').form('validate');">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px;text-align: left;">
			<gd:btn btncode='BTNGYSDBT04'><a class="easyui-linkbutton" iconCls="icon-save" id="icon-update">补贴</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<gd:btn btncode='BTNGYSDBT02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
	</div>
</body>
<script>
	var type = ${type};
</script>
<script type="text/javascript" src="${CONTEXT}js/supplier/main.js"></script>
</html>

