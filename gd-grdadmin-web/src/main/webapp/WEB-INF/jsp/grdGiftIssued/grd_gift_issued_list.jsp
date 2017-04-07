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
		<link href="${CONTEXT}css/uploader.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
	</head>
<body>

	<table id="grdGiftDataGrid" title=""></table>
	
	<div id="grdGiftToolBar" style="height:auto;padding:0px !important;margin:0px;">
		<form id="grdGiftIssuedForm" method="post" style="padding:5px 0px 5px 20px;">
			<div>
				<table  cellpadding="1">
					<tr>
						<td align="right">客户手机：</td>
						<td><input  type="text" id="mobile" class="easyui-validatebox" name="mobile" style="width:150px;margin-right: 10px;" placeholder="请输客户手机号码"/></td>
						<td align="right">状态：</td>
						<td>
							<select name="status" id="status" style="width:150px;height:20px;">
								<option value="" selected="selected">全部</option>
								<option value="0" >未发放</option>
								<option value="1" >已发放</option>
							</select>
						</td>
						<td align="right">用户类型：</td>
						<td>			
							<select name="userType" id="userType"  style="width:150px;height:20px;">
								<option value="" selected="selected">全部</option>
								<option value="1" >谷登农批</option>
								<option value="2" >农速通</option>
								<option value="3" >农商友</option>
								<option value="4" >供应商</option>
							</select>
						</td>
						<td align="right">发放时间：</td>
						<td>			
							<input  type="text"  id="startDateForGiftIssused" name="startDateForGiftIssused"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDateForGiftIssused\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDateForGiftIssused\')}'})"    style="width:80px" />
								—
							<input  type="text"    id="endDateForGiftIssused" name="endDateForGiftIssused"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateForGiftIssused\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDateForGiftIssused\')}'})"   style="width:80px"/> 
						</td>
					</tr>
					<tr>
						<td align="right">发放记录ID：</td>
						<td><input  type="text" id="recordId" class="easyui-validatebox" name="recordId" style="width:150px;margin-right: 10px;" placeholder="请输发放记录ID"/></td>
						<td align="right">发放人：</td>
						<td><input  type="text" id="grantUser" maxlength="30"  class="easyui-validatebox" name="grantUser" style="margin-right: 10px;" placeholder="请输入姓名"/></td>
						<td align="right">所属市场：</td>
						<td>
							<select name="belongToMarket" id="belongToMarket" style="width:150px;height:20px;margin-right: 10px;">
								<option value="" selected="selected">请选择</option>
								<c:forEach items="${requestScope.marketDTOs }" var="market">
									<option value="${market.id }" >${market.marketName }</option>
								</c:forEach>
							</select>
						</td>
						<td align="right">开单时间：</td>
						<td>
							<input  type="text"  id="startDateForGiftBill" name="startDateForGiftBill"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDateForGiftBill\')}'})"    onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDateForGiftBill\')}'})"    style="width:80px;" />
								—
							<input  type="text"  id="endDateForGiftBill" name="endDateForGiftBill"   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDateForGiftBill\')}'})"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDateForGiftBill\')}'})"   style="width:80px;margin-right: 10px;"/> 
						</td>
						<td>	
						</td>
					</tr>
					<tr>
						<td align="right">地推人员：</td>
						<td><input  type="text" id="createUser" maxlength="30" class="easyui-validatebox" name="createUser" style="width:150px;margin-right: 10px;" placeholder="请输入姓名"/></td>
					 	<td align="right">类型： </td>
					 	<td>
					 	<select name="type" id="type"  style="width:150px;height:20px;">
								<option value="" selected="selected">全部</option>
								<option value="1" >农批</option>
								<option value="2" >农速通</option>
							</select>
					 	</td>
					 	<td align="right">所属仓库：</td>
						<td>
							<select name="giftstoreId" id="giftstoreId" style="width:150px;height:20px;margin-right: 10px;">
								<option value="" selected="selected">全部</option>				
							</select>
						</td>
					 	<td></td>
						<td>
							<gd:btn btncode='BTNGRDGIFTISSUEDSL02'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="searchBtn">查询</a></gd:btn>
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="resetBtn">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</form>

		<div style="background:#efefef;padding:5px;height:25px;">
			<div style="float: left;font-size:16px;margin-left:5px;">礼品发放列表</div>
			<gd:btn btncode='BTNGRDGIFTISSUEDSL01'><div style="float:right;margin-right:57px;"><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></div></gd:btn>
		</div>
	</div>
	
	<div id="showDialog" class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit">
				<div style="text-align: left;margin-left: 126px;" id="divCarNo">
					车牌号：<input id="popWinCarNo" type="text" placeholder="请输入车牌号码" maxlength="10"/>
				</div>
				<div style="margin-top: 2px;">
		    		<a id="popWinSaveBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		        	<a id="popWinCloseBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#showDialog').dialog('close');">关闭</a>
		    	</div>
		    </div>
		</div>
		
</body>

<script type="text/javascript" src="${CONTEXT}js/grdGiftIssued/grd_gift_issued_list.js"></script>
</html>

