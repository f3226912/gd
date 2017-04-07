<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
	<head>
		<base href="${CONTEXT}">
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>活动管理</title>
	</head>
	<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>

	<body>
	<!-- 列表 -->
		<table id="datagrid-table" ></table>
	<div id="datagrid-tool-bar" style="padding:5px;height:auto">
		<!-- 查询条件 -->
		<form id="datagrid-form" method="post">
			<div>
				用户账号 : 
				<input type="text" id="memberAccount" name="memberAccount" class="easyui-validatebox" style="width:150px" >
				手机号码 : 
				<input type="text" id="memberMobile" name="memberMobile" class="easyui-validatebox" style="width:150px" >
				活动编号 : 
				<input type="text" id="activityCode" name="activityCode" class="easyui-validatebox" style="width:150px" >
				活动名称 : 
				<input type="text" id="activityName" name="activityName" class="easyui-validatebox" style="width:150px" >
				<br/>
				积分获取时间 : 
				<input  type="text"  id="startTime" name="startTime"
					onFocus="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"
					onClick="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"
					style="width:150px" >~
				<input  type="text" id="endTime" name="endTime"
					onFocus="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
					onClick="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"   
					style="width:150px">
					
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btnSearch" >查询</a>
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btnReset">重置</a>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="margin-bottom:5px">
			<a id="btnAdd" class="easyui-linkbutton icon-add-btn" iconCls="icon-add">新增</a>
			<a id="btnExport" class="easyui-linkbutton" iconCls="icon-save">导出</a>
			<a id="btnRefresh" class="easyui-linkbutton" iconCls="icon-reload" >刷新</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="clickSave()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
	</body>
	
	<script type="text/javascript" src="${CONTEXT}js/gdActActivity/userIntegral/list.js"></script>
</html>