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
	<table id="countCardg" title="">
	</table>
	<div id="countCartb" style="padding:5px;height:auto">
		<form id="countCarSearchForm" method="post">
			区域名称: <select id="areaName" name="areaName" class="text_sketch" >
		 					<option selected="selected" value="">全部</option>
		 					<c:forEach items="${areaNameList}" var="obj">
		  					<option value="${obj.areaName}">${obj.areaName}</option>        
		 					</c:forEach>
		 			  </select>&nbsp;&nbsp;
			推荐人电话: <input  type="text" id=mobile    class="easyui-validatebox"    name="mobile" style="width:150px" maxlength="11">
			被推荐人电话:<input  type="text" id=mobile_ed    class="easyui-validatebox"    name="mobile_ed" style="width:150px" maxlength="11"><br>
			被推荐人注册时间 :
			<input  type="text"  id="memberCreateBeginTime" name="memberCreateBeginTime"  
				onFocus="WdatePicker({onpicked:function(){memberCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'memberCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){memberCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'memberCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="memberCreateEndTime" name="memberCreateEndTime"   
				onFocus="WdatePicker({onpicked:function(){memberCreateEndTime.focus();},minDate:'#F{$dp.$D(\'memberCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){memberCreateEndTime.focus();},minDate:'#F{$dp.$D(\'memberCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})">
			发布车辆时间:
			<input  type="text"  id="carCreateBeginTime" name="carCreateBeginTime"  
				onFocus="WdatePicker({onpicked:function(){carCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'carCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){carCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'carCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="carCreateEndTime" name="carCreateEndTime"   
				onFocus="WdatePicker({onpicked:function(){carCreateEndTime.focus();},minDate:'#F{$dp.$D(\'carCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){carCreateEndTime.focus();},minDate:'#F{$dp.$D(\'carCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   style="width:150px"><br/>
			<gd:btn btncode='BTNXXTJGXCLFBTJ01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#countCarSearchForm').form('validate');">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNXXTJGXCLFBTJ02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</form>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/count/car.js"></script>
</html>

