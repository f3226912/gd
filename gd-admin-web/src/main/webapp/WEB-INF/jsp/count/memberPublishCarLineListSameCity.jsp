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
	<table id="countCarLinedg" title="">
	</table>
	<div id="countCarLinetb" style="padding:5px;height:auto">
		<form id="countCarLineSearchForm" method="post">
			区域名称: <select id="areaName" name="areaName" class="text_sketch" >
		 					<option selected="selected" value="">全部</option>
		 					<c:forEach items="${areaNameList}" var="obj">
		  					<option value="${obj.areaName}">${obj.areaName}</option>        
		 					</c:forEach>
		 			  </select>&nbsp;&nbsp;
			推荐人电话: <input  type="text" id=mobile    class="easyui-validatebox"    name="mobile" style="width:150px" maxlength="11">
			被推荐人电话:<input  type="text" id=mobile_ed    class="easyui-validatebox"    name="mobile_ed" style="width:150px" maxlength="11"><br>
			起始地省份：<select id="s_provinceId" name="s_provinceId" class="text_sketch" >
		 					<option selected="selected" value="">全省</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 			  </select>
		 	起始地城市：<select id="s_cityId" name="s_cityId" class="text_sketch" >
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
		 	起始地区域：<select id="s_areaId" name="s_areaId" class="text_sketch" >
		 					<option selected="selected" value="">选择区域</option>
		 				</select><br>
		 	目的地省份：<select id=e_provinceId name="e_provinceId" class="text_sketch" >
		 					<option selected="selected" value="">全省</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 			  </select>
		 	目的地城市：<select id="e_cityId" name="e_cityId" class="text_sketch" >
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
		 	目的地区域：<select id="e_areaId" name="e_areaId" class="text_sketch" >
		 					<option selected="selected" value="">选择区域</option>
		 				</select><br>
			被推荐人注册时间 :
			<input  type="text"  id="memberCreateBeginTime" name="memberCreateBeginTime"  
				onFocus="WdatePicker({onpicked:function(){memberCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'memberCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){memberCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'memberCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="memberCreateEndTime" name="memberCreateEndTime"   
				onFocus="WdatePicker({onpicked:function(){memberCreateEndTime.focus();},minDate:'#F{$dp.$D(\'memberCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){memberCreateEndTime.focus();},minDate:'#F{$dp.$D(\'memberCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})">
			发布线路时间:
			<input  type="text"  id="lineCreateBeginTime" name="lineCreateBeginTime"  
				onFocus="WdatePicker({onpicked:function(){lineCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'lineCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){lineCreateBeginTime.focus();},maxDate:'#F{$dp.$D(\'lineCreateEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="lineCreateEndTime" name="lineCreateEndTime"   
				onFocus="WdatePicker({onpicked:function(){lineCreateEndTime.focus();},minDate:'#F{$dp.$D(\'lineCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){lineCreateEndTime.focus();},minDate:'#F{$dp.$D(\'lineCreateBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   style="width:150px"><br/>
			<gd:btn btncode='BTNXXTJTCXLFBTJ01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#countCarLineSearchForm').form('validate');">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNXXTJTCXLFBTJ02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</form>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/count/main_same_city.js"></script>
</html>

