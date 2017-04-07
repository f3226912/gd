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
		<title>日志管理</title>
	</head>
<body>
	<table id="carsdg" title="">
	</table>
	<div id="carstb" style="padding:5px;height:auto">
		<form id="carsSearchForm" method="post">
		<div>
		      发布时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
		   交易流水号：
			<input  type="text" id="transseqno"  name="transseqno"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
		         状态：
		 <select id="state" name="state" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
		   <option value="1">请求原文</option>        
           <option value="2">请求成功 </option> 
           <option value="3">请求失败(无对应数据)</option>   
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
		<gd:btn btncode='BTNGXWLXXGLCLXXGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#carsSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='btn-reset'>重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNGXWLXXGLCLXXGL05'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		<div id="detailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/enPostLog/enPostLog.js"></script>

</html>

