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
		<title>同城订单统计</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="orderCountdg" title="">
	</table>
	<div id="orderCounttb" style="padding:5px;height:auto">
		<form id="orderCountSearchForm" method="post">
			      <input  type="hidden" id="transportType"    name="transportType"  value="1" />
			订单号: <input  type="text" id=orderNo    class="easyui-validatebox"    name="orderNo" style="width:150px">
			订单状态:<select name="orderStatus" id="orderStatus">
						<option value="">请选择</option>
						<option value="1">待成交</option>
						<option value="2">已成交</option>
						<option value="3">已完成</option>
						<option value="4">未完成</option>
						<option value="5">已取消</option>
					</select>
			<br>
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
		 	目的地省份：<select id=f_provinceId name="f_provinceId" class="text_sketch" >
		 					<option selected="selected" value="">全省</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 			  </select>
		 	目的地城市：<select id="f_cityId" name="f_cityId" class="text_sketch" >
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
		 	目的地区域：<select id="f_areaId" name="f_areaId" class="text_sketch" >
		 					<option selected="selected" value="">选择区域</option>
		 				</select><br>
		 	接单者电话: <input  type="text" id=receiveMobile    class="easyui-validatebox"    name="receiveMobile" style="width:150px" maxlength="11">
			发布者电话: <input  type="text" id=releaseMobile    class="easyui-validatebox"    name="releaseMobile" style="width:150px" maxlength="11"><br>
			发布者推荐人电话: <input  type="text" id=releaseMobile_ed    class="easyui-validatebox"    name="releaseMobile_ed" style="width:150px" maxlength="11">
			发布者推荐人所属区域:<select id="releaseAreaName" name="releaseAreaName" class="text_sketch" >
		 						<option selected="selected" value="">全部</option>
		 						<c:forEach items="${areaNameList}" var="obj">
		  						<option value="${obj.areaName}">${obj.areaName}</option>        
		 						</c:forEach>
		 			  			</select><br>
		 	接单者推荐人电话: <input  type="text" id=receiveMobile_ed    class="easyui-validatebox"    name="receiveMobile_ed" style="width:150px" maxlength="11">
		 	接单者推荐人所属区域:<select id="receiveAreaName" name="receiveAreaName" class="text_sketch" >
		 						<option selected="selected" value="">全部</option>
		 						<c:forEach items="${areaNameList}" var="obj">
		  						<option value="${obj.areaName}">${obj.areaName}</option>        
		 						</c:forEach>
		 			  			</select><br>
			
			 接单时间：
		     <input  type="text"  id="orderBeginTime" name="orderBeginTime"  onFocus="WdatePicker({onpicked:function(){orderBeginTime.focus();},maxDate:'#F{$dp.$D(\'orderEndTime\')}'})"    onClick="WdatePicker({onpicked:function(){orderBeginTime.focus();},maxDate:'#F{$dp.$D(\'orderEndTime\')}'})"    style="width:150px" >
			 ~
		 	<input  type="text"   id="orderEndTime" name="orderEndTime"   onFocus="WdatePicker({onpicked:function(){orderEndTime.focus();},minDate:'#F{$dp.$D(\'orderBeginTime\')}'})"   onClick="WdatePicker({onpicked:function(){orderEndTime.focus();},minDate:'#F{$dp.$D(\'orderBeginTime\')}'})"   style="width:150px"> </br>
		
			订单完成时间:
			    <input  type="text"  id="order_completeBeginTime" name="order_completeBeginTime"  onFocus="WdatePicker({onpicked:function(){order_completeBeginTime.focus();},maxDate:'#F{$dp.$D(\'order_completeEndTime\')}'})"    onClick="WdatePicker({onpicked:function(){order_completeBeginTime.focus();},maxDate:'#F{$dp.$D(\'order_completeEndTime\')}'})"    style="width:150px" >
			 ~
		 	    <input  type="text"   id="order_completeEndTime" name="order_completeEndTime"   onFocus="WdatePicker({onpicked:function(){order_completeEndTime.focus();},minDate:'#F{$dp.$D(\'order_completeBeginTime\')}'})"   onClick="WdatePicker({onpicked:function(){order_completeEndTime.focus();},minDate:'#F{$dp.$D(\'order_completeBeginTime\')}'})"   style="width:150px">
						订单确认时间:
			    <input  type="text"  id="order_confirmBeginTime" name="order_confirmBeginTime"  onFocus="WdatePicker({onpicked:function(){order_confirmBeginTime.focus();},maxDate:'#F{$dp.$D(\'order_confirmEndTime\')}'})"    onClick="WdatePicker({onpicked:function(){order_confirmBeginTime.focus();},maxDate:'#F{$dp.$D(\'order_confirmEndTime\')}'})"    style="width:150px" >
			 ~
		 	    <input  type="text"   id="order_confirmEndTime" name="order_confirmEndTime"   onFocus="WdatePicker({onpicked:function(){order_confirmEndTime.focus();},minDate:'#F{$dp.$D(\'order_confirmBeginTime\')}'})"   onClick="WdatePicker({onpicked:function(){order_confirmEndTime.focus();},minDate:'#F{$dp.$D(\'order_confirmBeginTime\')}'})"   style="width:150px"><br/>	
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#orderCountSearchForm').form('validate');">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</form>
	</div>
	<div id="orderDetailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#orderDetail">
		<div id="orderDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#orderDetailDialog').dialog('close')">关闭</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/count/order.js"></script>
</html>
