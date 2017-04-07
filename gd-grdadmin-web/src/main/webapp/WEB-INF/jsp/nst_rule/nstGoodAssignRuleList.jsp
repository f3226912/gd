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
		<title>notice</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="nstGoodAssignRuledg" title="">
	</table>
	<div id="nstGoodAssignRuletb" style="padding:5px;height:auto">
		<input type="hidden" id="rules"/>
		<form id="nstGoodAssignRuleSearchForm" method="post">
			账号：<input  type="text" id='account'    name="account"  class="easyui-validatebox" placeholder="请输入账号" > <br>
			省份：<select id="s_provinceId" name="s_provinceId" class="text_sketch" >
		 					<option selected="selected" value="">全省</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 			  </select>
		 	城市：<select id="s_cityId" name="s_cityId" class="text_sketch" >
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
		 	信息部：<select id="s_areaId" name="s_areaId" class="text_sketch" >
		 					<option selected="selected" value="">选择信息部</option>
		 		  </select>
		 	<span id="span1">规则状态：</span><select id="isEffective" name="isEffective" class="text_sketch" >
		 					<option  value="">请选择</option>
		 					<option  value="0">有效</option>
		 					<option  value="1">无效</option>
		 		  </select><br>
			分配有效开始时间:<input  type="text"  id="assignStartBeginTime" name="assignStartBeginTime"  
				onFocus="WdatePicker({onpicked:function(){assignStartBeginTime.focus();},maxDate:'#F{$dp.$D(\'assignStartEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignStartBeginTime.focus();},maxDate:'#F{$dp.$D(\'assignStartEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="assignStartEndTime" name="assignStartEndTime"   
				onFocus="WdatePicker({onpicked:function(){assignStartEndTime.focus();},minDate:'#F{$dp.$D(\'assignStartBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignStartEndTime.focus();},minDate:'#F{$dp.$D(\'assignStartBeginTime\')}',dateFmt:'yyyy-MM-dd'})"><br>
			分配有效结束时间:<input  type="text"  id="assignEndBeginTime" name="assignEndBeginTime"  
				onFocus="WdatePicker({onpicked:function(){assignEndBeginTime.focus();},maxDate:'#F{$dp.$D(\'assignEndEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignEndBeginTime.focus();},maxDate:'#F{$dp.$D(\'assignEndEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >~
			<input  type="text"    id="assignEndEndTime" name="assignEndEndTime"   
				onFocus="WdatePicker({onpicked:function(){assignEndEndTime.focus();},minDate:'#F{$dp.$D(\'assignEndBeginTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignEndEndTime.focus();},minDate:'#F{$dp.$D(\'assignEndBeginTime\')}',dateFmt:'yyyy-MM-dd'})" ><br/>
			分配数查询开始时间:<input  type="text"  id="assignSumQueryStartTime" name="assignSumQueryStartTime"  
				onFocus="WdatePicker({onpicked:function(){assignSumQueryStartTime.focus();},maxDate:'#F{$dp.$D(\'assignSumQueryEndTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignSumQueryStartTime.focus();},maxDate:'#F{$dp.$D(\'assignSumQueryEndTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:150px" >
			分配数查询结束时间:<input  type="text"    id="assignSumQueryEndTime" name="assignSumQueryEndTime"   
				onFocus="WdatePicker({onpicked:function(){assignSumQueryEndTime.focus();},minDate:'#F{$dp.$D(\'assignSumQueryStartTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignSumQueryEndTime.focus();},minDate:'#F{$dp.$D(\'assignSumQueryStartTime\')}',dateFmt:'yyyy-MM-dd'})" ><br/>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#nstGoodAssignRuleSearchForm').form('validate');">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<a class="easyui-linkbutton icon-ok-btn" iconCls="icon-ok" id="icon-ok" >启用</a>
			<a class="easyui-linkbutton icon-no-btn" iconCls="icon-no" id="icon-no" >禁用</a>
			<a class="easyui-linkbutton" iconCls="icon-add" id="btn-add">规则设置</a>
		</form>
	</div>
	<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#addDetail">
		<div id="addDetail" style="text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btn-save" onclick="addSave()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
		</div>
	</div>
		<div id="assignDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#assignDetail">
		<div id="assignDetail" style="text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btn-save-assign" onclick="btnsaveassign()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#assignDialog').dialog('close')">关闭</a>
		</div>
	</div>
		<div id="updateDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#updateDetail">
		<div id="updateDetail" style="text-align:center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="btn-save-update" onclick="btnsaveupdate()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#updateDialog').dialog('close')">关闭</a>
		</div>
	</div>
	</div>
		<div id="detailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#detail">
		<div id="detail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
		</div>
	</div>
	<div id="deptGoodDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons=""></div>
</body>
<script type="text/javascript" src="${CONTEXT}js/nst_rule/main.js"></script>
</html>
