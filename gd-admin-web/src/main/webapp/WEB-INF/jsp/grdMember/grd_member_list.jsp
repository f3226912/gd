<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>礼品发放管理</title>
	</head>
<body>

	<table id="grdMembertDataGrid" title=""></table>
	
	<div id="grdMemberToolBar" style="height:auto;padding:0px !important;margin:0px;">
		<form id="grdMemberForm" method="post" style="padding:5px 0px 5px 20px;">
			<div>
				<table  cellpadding="1">
					<tr>
						<td align="right">所属市场：</td>
						<td>
							<select name="marketId" id="marketId" style="width:150px;height:20px;margin-right: 10px;">
								<option value="" selected="selected">请选择</option>
								<c:forEach items="${requestScope.marketDTOs }" var="market">
									<option value="${market.id }" >${market.marketName }</option>
								</c:forEach>
							</select>
						</td>
						<td align="right">团队：</td>
						<td>
							<select name="giftteamId" id="giftteamId" style="width:150px;height:20px;margin-right: 10px;">
								<option value="" selected="selected">全部</option>
							</select>
						</td>
						<td align="right">手机：</td>
						<td><input  type="text" id="mobile" class="easyui-validatebox" name="mobile" style="width:150px;margin-right: 10px;" placeholder="请输手机号码"/></td>
						
						<td align="right">注册时间：</td>
						<td>
						 <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:120px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:120px"> 
						
						</td>
					</tr>
					<tr>
						<td align="right">姓名：</td>
						<td><input  type="text" id="name" class="easyui-validatebox" name="name" style="width:150px;margin-right: 10px;" placeholder="请输入姓名"/></td>
						
						<td align="right"></td>
						<td></td>
						<td align="right"></td>
						<td></td>
						<td align="right"></td>
						<td>
							<gd:btn btncode='BTNGRDMEMBERSL06'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="searchBtn">查询</a></gd:btn>
							<gd:btn btncode='BTNGRDMEMBERSL08'><a  class="easyui-linkbutton" iconCls="icon-reload" id="reBtn">刷新</a></gd:btn>
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="resetBtn">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</form>

		<div style="background:#efefef;padding:5px;height:25px;">
			<div style="float: left;font-size:16px;margin-left:5px;">地推人员列表</div>
			<div style="float:right;margin-right:57px;">
				<gd:btn btncode='BTNGRDMEMBERSL02'><a id="addDataBtn" class="easyui-linkbutton" iconCls="icon-add">新增</a></gd:btn>
				<gd:btn btncode='BTNGRDMEMBERSL01'><a id="exportDataBtn" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
				<gd:btn btncode='BTNGRDMEMBERSL05'><a id="resetPwdBtn" class="easyui-linkbutton" iconCls="icon-reload">重置密码</a></gd:btn>
					<gd:btn btncode='BTNGRDMEMBERSL07'><a id="disableBtn" class="easyui-linkbutton" iconCls="icon-reload">禁用/启用</a></gd:btn>
				<gd:btn btncode='BTNGRDMEMBERSL04'><a id="removeDataBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a></gd:btn>
			</div>
		</div>
	</div>
	
	
</body>


<script type="text/javascript" src="${CONTEXT}js/grdMember/grd_member_list.js"></script>

</html>

