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
		<title>车辆专线管理</title>
	</head>
<body>
	<table id="carLinedg" title="">
	</table>
	<div id="carLinetb" style="padding:5px;height:auto">
		<form id="carLineSearchForm" action="carLine/export" method="post">
		<div>
		           发布时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
		       车辆类型：
		  <select id="queryCarType" name="queryCarType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
		   <option value="0">厢式货车</option>        
           <option value="1">敞车</option>            
           <option value="5">高栏车</option>          
           <option value="6">平板车</option>          
           <option value="2">冷藏车</option>          
           <option value="3">保温车</option>          
           <option value="7">活鲜水车</option>        
           <option value="8">集装箱</option>          
           <option value="4">其他</option>            
		   </select>&nbsp;&nbsp;
          联系电话：
			<input  type="text" id="mobile"  name="mobile"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			
		       用户类型： 	
		  <select id="queryType" name="queryType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="1">个人 </option>
          <option  value="2">企业</option>
		  </select>&nbsp;&nbsp;
		     区域名称: 
		 <select id="areaName" name="areaName" class="text_sketch" >
		 <option selected="selected" value="">全部</option>
		 <c:forEach items="${areaNameList}" var="obj">
		  <option value="${obj.areaName}">${obj.areaName}</option>        
		 </c:forEach>
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
			<gd:btn btncode='BTNGXWLXXGLXLXXGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#carLineSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNGXWLXXGLXLXXGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<gd:btn btncode='BTNGXWLXXGLXLXXGL03'><a class="easyui-linkbutton icon-edit-btn" iconCls="icon-edit" id="icon-edit" >编辑</a></gd:btn>
			<gd:btn btncode='BTNGXWLXXGLXLXXGL04'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNGXWLXXGLXLXXGL05'><a class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-save" id="btn-export">导出数据</a></gd:btn>
			<a class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-save" id="isDelExportData">导出已删除数据</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
		  <div id="carDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsCar">
			<div id="#dlg-buttonsCar" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-cars-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#carDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNGXWLXXGLXLXXGL06'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/carLine/car.js"></script>
</html>

