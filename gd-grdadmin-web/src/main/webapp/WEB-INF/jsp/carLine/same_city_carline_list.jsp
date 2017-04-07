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
		<title>同城车辆专线管理</title>
	</head>
<body>
	<table id="same_city_carLinedg" title="">
	</table>
	<div id="same_city_carLinetb" style="padding:5px;height:auto">
		<form id="same_city_carLineSearchForm" action="export" method="post">
		<div>
		           发布时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
		       车辆类型：
		  <select id="queryCarType" name="queryCarType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
		  <option  value="0">小型面包</option>
          <option  value="1">金杯</option>
          <option  value="2">小型平板</option>
          <option  value="3">中型平板</option>
          <option  value="4">小型厢货</option>
          <option  value="5">大型厢货</option>
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
			<gd:btn btncode='BTNTCWLXXGLXLXXGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#same_city_carLineSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNTCWLXXGLXLXXGL02'><a class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-save" id="btn-export">导出数据</a></gd:btn>
			<a class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-save" id="isDelExportData">导出已删除数据</a>
		</div>
		
		
		
	
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNTCWLXXGLXLXXGL03'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/carLine/sameCityCarline.js"></script>
</html>

