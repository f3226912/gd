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
	<table id="instoredg" title="">
	</table>
	<div id="instoretb" style="padding:5px;height:auto">
		<form id="instoreSearchForm" method="post">
			批发商账号: <input  type="text" id=specialAccount    class="easyui-validatebox"    name="specialAccount" style="width:150px" >
			供应商账号: <input  type="text" id=supplierAccount    class="easyui-validatebox"    name="supplierAccount" style="width:150px" >
			入库单:<input  type="text" id=inStoreNo    class="easyui-validatebox"    name="inStoreNo" style="width:150px" >
			创建人: <input  type="text" id=createName    class="easyui-validatebox"    name="createName" style="width:150px" ><br>
			产品创建时间 :
			<input  type="text"  id="productStartTime" name="productStartTime"  
				onFocus="WdatePicker({onpicked:function(){productStartTime.focus();},maxDate:'#F{$dp.$D(\'productEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){productStartTime.focus();},maxDate:'#F{$dp.$D(\'productEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="productEndTime" name="productEndTime"   
				onFocus="WdatePicker({onpicked:function(){productEndTime.focus();},minDate:'#F{$dp.$D(\'productStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){productEndTime.focus();},minDate:'#F{$dp.$D(\'productStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			入库状态:<select id="istoreStatus" name="istoreStatus">
						<option value="-1">请选择</option>
						<option value="0">未入库</option>
						<option value="1">已入库</option>
					</select>
			<gd:btn btncode='BTNJYYRKCPGLRKCPGL02'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#instoreSearchForm').form('validate');">查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNJYYRKCPGLRKCPGL01'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</form>
	</div>
	
</body>
<script type="text/javascript" src="${CONTEXT}js/instore/main.js"></script>
</html>

