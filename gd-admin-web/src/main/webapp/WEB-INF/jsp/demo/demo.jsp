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
		<title>demo</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="demodg" title="">
	</table>
	<div id="demotb" style="padding:5px;height:auto">
		<form id="demoSearchForm" method="post">
		<div>
			名称: 
			<input  type="text" id="name"    class="easyui-validatebox" missingMessage="name必须填写"   name="name" style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			生日范围：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px"> 
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#demoSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='location.reload(true)'">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<xlc:btn btncode='XZ001'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></xlc:btn>
			<xlc:btn btncode='XZ002'><a class="easyui-linkbutton icon-edit-btn" iconCls="icon-edit" id="icon-edit" >编辑</a></xlc:btn>
			<xlc:btn btncode='XZ003'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></xlc:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick='location.reload(true)'">刷新</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/demo/demo.js"></script>
</html>

