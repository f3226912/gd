<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>promotion</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="promotiondg" title="">
	</table>
	<div id="promotiontb" style="padding:5px;height:auto">
		<form id="promotionSearchForm" method="post">
		<div>
			 渠道名称:
			<input  type="text" id="name" class="easyui-validatebox" name="name" style="width:150px" >
			 创建时间 :
			<input  type="text"  id="startDate" name="startDate"
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
				style="width:150px" >~
			<input  type="text"    id="endDate" name="endDate"
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
			<gd:btn btncode='BTNTJXXQDTJ01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="serachList" OnClick ="return $('#promotionSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>

		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNTJXXQDTJ02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>

	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/promotion/statistics.js"></script>

</html>

