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
		<title>积分流水管理</title>
	</head>
<body>
	<table id="integraldg" title=""></table>
	<div id="integraltb" style="padding:5px;height:auto">
	<form id="integralForm" method="post">
		<div>
		    <gd:btn btncode='BTNLPGLJFXX01'> 活动：<input type="button" id="btn-activity-select" value="选择活动"/></gd:btn>&nbsp;&nbsp;
		    <input type="hidden" id="activityId" value=""/>
		           账号：
			<input type="text" id="memberAccount"  name="memberAccount"  style="width:150px" >&nbsp;&nbsp;
			用户类型：
			<select id="userType" name="userType">
				<option value="">全部</option>
				<option value="1">个人</option>
				<option value="2">企业</option>
			</select>&nbsp;&nbsp;
			积分类型：
			<select id="type" name="type">
				<option value="">全部</option>
				<option value="1">推广积分</option>
				<option value="2">积分兑换</option>
				<option value="3">推荐人绑定</option>
				<option value="4">管理员管理积分</option>
			</select>&nbsp;&nbsp;<br/><br/>
			创建时间：
			 <input  type="text" id="queryStartDate" name="queryStartDate" onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})" style="width:150px" >
			~
			<input  type="text" id="queryEndDate" name="queryEndDate" onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})" style="width:150px">&nbsp;&nbsp; 
			<gd:btn btncode='BTNLPGLJFXX02'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="integral-btn-search">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='integral-btn-reset'>重置</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="integral-btn-refresh">刷新</a>&nbsp;&nbsp;
		</div>
	</form>
	</div>
	<div id="activityDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMarket">
		<div id="#dlg-buttonsMarket" style="text-align:center">
	       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#marketDialog').dialog('close')">关闭</a>
	       </div>
	</div>
<script type="text/javascript">
function optformat(value,row,index){
	if(row.type == "2" && row.isReturn != 1 ){
		return "<gd:btn btncode='BTNLPGLJFXX03'><a class='operate' href='javascript:;' onClick='integralReturn("+row.id+")'>积分回退</a></gd:btn>";
	}else{
		return "";
	}
}
</script>
	<script type="text/javascript" src="${CONTEXT}js/integral/main.js"></script>
</body>
</html>