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
		<title>notice</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="nstNoticedg" title="">
	</table>
	<div id="nstNoticetb" style="padding:5px;height:auto">
		<form id="nstNoticeSearchForm" method="post">
			开始时间:<input  type="text"  id="startBeginTime" name="startBeginTime"  
				onFocus="WdatePicker({onpicked:function(){startBeginTime.focus();},maxDate:'#F{$dp.$D(\'startEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){startBeginTime.focus();},maxDate:'#F{$dp.$D(\'startEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="startEndTime" name="startEndTime"   
				onFocus="WdatePicker({onpicked:function(){startEndTime.focus();},minDate:'#F{$dp.$D(\'startBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){startEndTime.focus();},minDate:'#F{$dp.$D(\'startBeginTime\')}',dateFmt:'yyyy-MM-dd'})"><br>
			结束时间:<input  type="text"  id="endBeginTime" name="endBeginTime"  
				onFocus="WdatePicker({onpicked:function(){endBeginTime.focus();},maxDate:'#F{$dp.$D(\'endEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){endBeginTime.focus();},maxDate:'#F{$dp.$D(\'endEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="endEndTime" name="endEndTime"   
				onFocus="WdatePicker({onpicked:function(){endEndTime.focus();},minDate:'#F{$dp.$D(\'endBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){endEndTime.focus();},minDate:'#F{$dp.$D(\'endBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   style="width:150px"><br/>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#nstNoticeSearchForm').form('validate');">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<a class="easyui-linkbutton" iconCls="icon-add" id="btn-add">增加</a>
		</form>
	</div>
	<div id="nstNoticeDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#nstNoticeDetail">
		<div id="nstNoticeDetail" style="text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btn-save" >保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#nstNoticeDialog').dialog('close')">关闭</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/notice/main.js"></script>
</html>
