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
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
		<title>待补贴订单</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="subauditdg" title="">
	</table>
	<div id="pushrectb" style="padding:5px;height:auto">
		<!-- <input type="hidden" id="subStatus" name="subStatus" value="${subStatus }"/>  -->
		<form id="subauditSearchForm" method="post">
		<div>

			订&nbsp;&nbsp;单&nbsp;&nbsp;号：<input  type="text" id="orderNo" class="easyui-validatebox" name="orderNo" style="width:150px" >
			&nbsp;&nbsp;订单金额：<input  type="text" id="orderAmount" class="easyui-validatebox" name="orderAmount" style="width:150px" >
			&nbsp;&nbsp;付款方式：
			<select name="payType" id="payType">
				<option value="" selected="selected">---请选择---</option>
				<option value="1" >钱包余额</option>
				<option value="2" >线下刷卡</option>
				<option value="3">现金</option>
				<option value="12" >钱包余额+线下刷卡</option>
				<option value="13" >钱包余额+现金</option>
			</select>
			&nbsp;&nbsp;买家姓名：<input  type="text" id="buyerName" class="easyui-validatebox" name="buyerName" style="width:150px" />

			<c:choose>
				<c:when test="${subStatus eq 0 }">
					&nbsp;&nbsp;补贴状态：
					<select name="subStatus" id="subStatus"  >
						<option value="">---请选择---</option>
						<option value="1" <c:if test="${subStatus eq 1 }">selected="selected"</c:if>>待补贴</option>
						<option value="2" <c:if test="${subStatus eq 2 }">selected="selected"</c:if>>系统驳回</option>
						<option value="3" <c:if test="${subStatus eq 3 }">selected="selected"</c:if>>已补贴</option>
						<option value="4" <c:if test="${subStatus eq 4 }">selected="selected"</c:if>>不予补贴</option>
					</select>
				</c:when>
				<c:otherwise>
					<input type="hidden" id="subStatus" name="subStatus" value="${subStatus }"/>
				</c:otherwise>
			</c:choose>

			<br/>
			卖家商铺：<input type="text" id="buyerShop" class="easyui-validatebox" name="buyerShop" style="width:150px;" />
			&nbsp;&nbsp;
			<input id="orderLike" name="orderLike" class="easyui-textbox " placeholder="订单详情搜索" style="width: 210px;" />
			&nbsp;
			<gd:btn btncode='BTNDBTYZDBTYZDD04'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#pushRecForm').form('validate');">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>

		<div style="margin-bottom:5px;text-align: left;">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<c:if test="${subStatus==1 }">
				<gd:btn btncode='BTNDBTYZDBTYZDD01'><a id="verifySelected" class="easyui-linkbutton" >批量通过审核</a></gd:btn>
			</c:if>
 			<gd:btn btncode='BTNDBTYZDBTYZDD02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>

		<div id="showDialog" class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttonsEdit" data-options="onClose:function(){return resumeIsSubmited();}">
			<div id="dlg-buttonsEdit" style="text-align:center">
		    	<gd:btn btncode='BTNDBTYZDBTYZDD06'><a id="passBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="verify(1);">通过审核</a></gd:btn>
		        <gd:btn btncode='BTNDBTYZDBTYZDD07'><a id="failBtn" href="javascript:void(0)" class="easyui-linkbutton" onclick="verify(0);">审核不通过</a></gd:btn>
		    </div>
		</div>
	</div>
</body>
<script>
	var subStatus = ${subStatus};
	function optformat(value,row,index){
		return "<gd:btn btncode='BTNDBTYZDBTYZDD03'><a class='operate' href='javascript:;' onclick=showDetail('"+row.auditId+"');>详细</a></gd:btn>";
	}
	var disableExport = false ;
</script>
<script type="text/javascript" src="${CONTEXT}js/subaudit/main.js"></script>
</html>

