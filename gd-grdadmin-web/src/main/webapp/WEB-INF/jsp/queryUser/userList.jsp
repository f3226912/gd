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
                                    推荐人电话: 
			<input  type="text" id="mobile"  name="mobile"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			被推荐人电话: 
			<input  type="text" id="recommendedMobile"  name="recommendedMobile"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
	                       被推荐人注册时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px">  &nbsp;&nbsp;&nbsp;&nbsp;
			
			车辆发布时间：
		     <input  type="text"  id="queryCarStartDate" name="queryCarStartDate"  onFocus="WdatePicker({onpicked:function(){queryCarStartDate.focus();},maxDate:'#F{$dp.$D(\'queryCarEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryCarStartDate.focus();},maxDate:'#F{$dp.$D(\'queryCarEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryCarEndDate" name="queryCarEndDate"   onFocus="WdatePicker({onpicked:function(){queryCarEndDate.focus();},minDate:'#F{$dp.$D(\'queryCarStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryCarEndDate.focus();},minDate:'#F{$dp.$D(\'queryCarStartDate\')}'})"   style="width:150px"> &nbsp;&nbsp;&nbsp;&nbsp;
			
			 线路发布时间：
		     <input  type="text"  id="queryCarLineStartDate" name="queryCarLineStartDate"  onFocus="WdatePicker({onpicked:function(){queryCarLineStartDate.focus();},maxDate:'#F{$dp.$D(\'queryCarLineEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryCarLineStartDate.focus();},maxDate:'#F{$dp.$D(\'queryCarLineEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryCarLineEndDate" name="queryCarLineEndDate"   onFocus="WdatePicker({onpicked:function(){queryCarLineEndDate.focus();},minDate:'#F{$dp.$D(\'queryCarLineStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryCarLineEndDate.focus();},minDate:'#F{$dp.$D(\'queryCarLineStartDate\')}'})"   style="width:150px"> 
			</br>
			认证状态: 
				<select name="nstStatus"  id="nstStatus" >
  							<option value="">全部</option>
 							<option value="1" >已认证</option>
							<option value="0" >待认证</option>
							<option value="2" >认证不通过</option>
				</select>
			<gd:btn btncode='BTNXXTJHYXLCX01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#userSearchForm').form('validate');">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='btn-reset'>重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNXXTJHYXLCX02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/queryUser/user.js"></script>

</html>

