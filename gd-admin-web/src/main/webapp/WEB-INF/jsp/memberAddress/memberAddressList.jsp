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
		<title>收发货管理</title>
	</head>
<body>
	<table id="consignmentdg" title="">
	</table>
	<div id="consignmenttb" style="padding:5px;height:auto">
		<form id="consignmentSearchForm" method="post">
		<div>
			 发货人: 
			<input  type="text" id="realName"  name="realName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			
			  发布时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
	                  用户类型: 	
		  <select id="queryType" name="queryType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="1">个人 </option>
          <option  value="2">企业</option>
		  </select>&nbsp;&nbsp;</br>
		  常用城市: 
		 <input  type="text" id="cityName"  name="cityName"  style="width:150px" >&nbsp;&nbsp;
		  订单状态: 	
		  <select id="orderStatus" name="orderStatus" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="1">待成交 </option>
          <option  value="2">已成交</option>
          <option  value="3">已完成</option>
          <option  value="4">未完成</option>
		  </select>&nbsp;&nbsp;
		 
		   发布来源：
			<!-- 1谷登农批，2农速通，3农商友，4产地供应商，5门岗' -->
		    <select id="clients" name="clients" class="text_sketch" >
		      <option selected="selected" value="">全部</option>
		      <option  value="1">谷登农批</option>
		      <option  value="2">农速通</option>
		      <option  value="3">农商友</option>
		      <option  value="4">产地供应商</option>
		      <option  value="5">农商友-农批商</option>
		    </select>&nbsp;&nbsp;&nbsp;&nbsp;
		  
		     订单是否删除: 	
		  <select id="isOrderDeleted" name="isOrderDeleted" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="0">未删除 </option>
          <option  value="1">已删除</option>
		  </select>&nbsp;&nbsp;
		  
			<gd:btn btncode='BTNGXWLXXGLHYXXGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#consignmentSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
<%-- 	<gd:btn btncode='BTNWLXXHYGL01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
 --%>		
            <gd:btn btncode='BTNGXWLXXGLHYXXGL02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
         	<gd:btn btncode='BTNGXWLXXGLHYXXGL03'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
         	<a id="exportIsDelData" class="easyui-linkbutton" iconCls="icon-save">导出已删除数据</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
		<div id="entDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsCar">
			<div id="#dlg-buttonsCar" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-cars-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#entDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	 return "<gd:btn btncode='BTNGXWLXXGLHYXXGL04'><a class='operate' href='javascript:;'onclick=editObj('"+row.id+"');>修改</a></gd:btn>&nbsp;" +
	 "<gd:btn btncode='BTNGXWLXXGLHYXXGL05'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/memberAddress/memberAddress.js"></script>
<script type="text/javascript" src="${CONTEXT}js/memberAddress/area.js"></script>

</html>

