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
		<title>车辆管理</title>
	</head>
<body>
	<table id="carsdg" title="">
	</table>
	<div id="carstb" style="padding:5px;height:auto">
		<form id="carsSearchForm" method="post">
		<div>
	     <input  type="hidden" id="transportType"    name="transportType"  value="1" /> 
		          车辆类型: 
		 <select id="carType" name="carType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
		  <option  value="0">小型面包</option>
          <option  value="1">金杯</option>
          <option  value="2">小型平板</option>
          <option  value="3">中型平板</option>
          <option  value="4">小型厢货</option>
          <option  value="5">大型厢货</option>
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
			<!--  联系人: 
			<input  type="text" id="userName"  name="userName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
             -->
                                    联系电话: 
			<input  type="text" id="phone"  name="phone"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			
			  车牌号码: 
			<input  type="text" id="carNumber"  name="carNumber"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			
			  用户类型: 	
		  <select id="queryType" name="queryType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="1">个人 </option>
          <option  value="2">企业</option>
		  </select>&nbsp;&nbsp;
	                      发布时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
			<gd:btn btncode='BTNTCWLXXGLCLXXGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#carsSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='btn-reset'>重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNTCWLXXGLCLXXGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<gd:btn btncode='BTNTCWLXXGLCLXXGL03'><a class="easyui-linkbutton icon-edit-btn" iconCls="icon-edit" id="icon-edit" >编辑</a></gd:btn>
			<gd:btn btncode='BTNTCWLXXGLCLXXGL04'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNTCWLXXGLCLXXGL05'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNTCWLXXGLCLXXGL06'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
}

</script>
<script type="text/javascript" src="${CONTEXT}js/cars/car.js"></script>

</html>

