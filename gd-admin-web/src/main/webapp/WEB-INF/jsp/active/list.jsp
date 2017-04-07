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
		<table id="activedg" title=""></table>
		<div id="activetb" style="padding-top:10px;height:auto">
			<form id="activeSearchForm" method="post">
				<div>
					<label>活动名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" name="name"/></label>
					<label>活动日期：
						<input type="text" name="actStartDate" id="actStartDate" onFocus="WdatePicker({onpicked:function(){actStartDate.focus();},maxDate:'#F{$dp.$D(\'actEndDate\')}'})" onClick="WdatePicker({onpicked:function(){actStartDate.focus();},maxDate:'#F{$dp.$D(\'actEndDate\')}'})"/> - 
						<input type="text" name="actEndDate" id="actEndDate" onFocus="WdatePicker({onpicked:function(){actEndDate.focus();},minDate:'#F{$dp.$D(\'actStartDate\')}'})" onClick="WdatePicker({onpicked:function(){actEndDate.focus();},minDate:'#F{$dp.$D(\'actStartDate\')}'})" >
					</label>
					<br/>
					<label>活动所属市场：
						<input type="text" id="queryMarketId" style="width:150px"  name="queryMarketId">
					</label>
					<label>创建日期：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label>
					<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>
					
				</div>
				<div style="margin-top:10px">
					<gd:btn btncode='BTNHDCXDJ01'>
						<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add">新增</a>
					</gd:btn>
					<gd:btn btncode='BTNHDCXDJ03'>
						<a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出EXCEL</a>
					</gd:btn>
					
				</div>
			</form>
		</div>
		<div style="display:none">
			<iframe id="ifact" src=""></iframe>
		</div>
		<!-- 弹出详情对话框 -->
	<div id="win" class="easyui-window" title="活动详情" style="width:900px;height:450px"
		data-options="modal:true,collapsible:false,minimizable:false,top:10,
		 maximizable:false,closed:true,onClose:closeWindow">
	</div>
	<script type="text/javascript">
		var canEdit = ${canEdit}; //1 可以修改 2不能修改
		$.ajaxSetup({ cache: false });//IE下ajax请求会被缓存
	</script>
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/active/list.js?version=${version}"></script>
	</body>
</html>