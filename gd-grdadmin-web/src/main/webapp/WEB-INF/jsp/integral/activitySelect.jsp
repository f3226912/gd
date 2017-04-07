<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table id="activitydg" title=""></table>
<div id="activitytb" style="padding:5px;height:auto">
<form id="activityForm" method="post">
	<div>
	           活动名称：<input type="text" id="name" name="name"/>&nbsp;&nbsp;     
		<!-- 活动时间：
		<input type="text" id="startDate" name="queryStartDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" style="width:150px" >
		~
		<input type="text" id="endDate" name="queryEndDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" style="width:150px">&nbsp;&nbsp;  -->
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="activity-btn-search">查询</a>&nbsp;&nbsp;
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='activity-btn-reset' onclick="$('#activityForm')[0].reset();">重置</a>
	</div>
</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/integral/activity.js"></script>
