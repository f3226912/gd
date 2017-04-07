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
		<title>用户统计</title>
	</head>
<body>
	<table id="userdg" title="">
	</table>
	<div id="usertb" style="padding:5px;height:auto">
		<form id="userSearchForm" method="post">
		<div>
		   区域名称: 
		 <select id="areaName" name="areaName" class="text_sketch" >
		 <option selected="selected" value="">全部</option>
		 <c:forEach items="${areaNameList}" var="obj">
		  <option value="${obj.areaName}">${obj.areaName}</option>        
		 </c:forEach>
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
		 是否被推荐：
		  <select id="isReferees" name="isReferees" class="text_sketch" >
		  	<option selected="selected" value="">全部</option>
		  	<option value="1">是</option>        
		 	<option value="0">否</option>  
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
                                    推荐人电话: 
			<input  type="text" id="mobile"  name="mobile"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			被推荐人电话: 
			<input  type="text" id="recommendedMobile"  name="recommendedMobile"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
	                       被推荐人注册时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px">  &nbsp;&nbsp;<br/>
			
			发布货源时间：
		     <input  type="text"  id="queryAddressStartDate" name="queryAddressStartDate"  onFocus="WdatePicker({onpicked:function(){queryAddressStartDate.focus();},maxDate:'#F{$dp.$D(\'queryAddressEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryAddressStartDate.focus();},maxDate:'#F{$dp.$D(\'queryAddressEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryAddressEndDate" name="queryAddressEndDate"   onFocus="WdatePicker({onpicked:function(){queryAddressEndDate.focus();},minDate:'#F{$dp.$D(\'queryAddressStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryAddressEndDate.focus();},minDate:'#F{$dp.$D(\'queryAddressStartDate\')}'})"   style="width:150px"> </br>
	                    始发地省:<input  name="start_provinceId" id="start_provinceId"
					style="width: 174px;" 
					class="easyui-validatebox">
		     始发地市:
				<input name="start_cityId" id="start_cityId"
					style="width: 174px;" 
					class="easyui-validatebox">
		  始发地县区:<input name="start_areaId" id="start_areaId"
					style="width: 174px;" class="easyui-validatebox"> </br>
		 目的地省:<input name="end_provinceId" id="end_provinceId"
					style="width: 174px;" 
					class="easyui-validatebox">
	            目的地市:<input name="end_cityId" id="end_cityId"
					style="width: 174px;" 
					class="easyui-validatebox">
		目的地县区:<input name="end_areaId" id="end_areaId"
					style="width: 174px;" class="easyui-validatebox">&nbsp;&nbsp;&nbsp;&nbsp;
			
			
			<gd:btn btncode='BTNXXTJGXHYFBTJ01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#userSearchForm').form('validate');">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='btn-reset'>重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNXXTJGXHYFBTJ02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/queryUserByAddress/user.js"></script>
<script type="text/javascript" src="${CONTEXT}js/queryUserByAddress/area.js"></script>


</html>

