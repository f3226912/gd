<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible"
	content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<title>农批商询价管理</title>
</head>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="datagrid-table" title=""></table>
	<div id="datagrid-tool-bar" style="padding: 5px; height: auto">
		<form id="datagrid-form" method="post">
			<div>
				<table>
					<tr>
						<td>编号：</td>
						<td><input type="text" id="id" name="id" placeholder="编号"
							class="easyui-validatebox" style="width: 150px">
							</td>
						<td>手机号码：</td>
						<td><input type="text" id="mobilePhone" name="mobilePhone"
							placeholder="手机号码" class="easyui-validatebox"
							style="width: 150px"></td>
						<td>商品名称：</td>
						<td><input type="text" id="goodsName" name="goodsName"
							placeholder="商品名称" class="easyui-validatebox"
							style="width: 150px"></td>
						<td>状态：</td>
						<td><select id="status" name="status">
								<option value="">-请选择-</option>
								<option value="1">待审核</option>
								<option value="2">审核通过</option>
								<option value="3">已驳回</option>
								<option value="4">用户撤销</option>
								<option value="6">已结束</option>
						</select></td>
						<td>农批市场 ：</td>
						<td><input type="text" id="chooseMarket" class="easyui-textbox" placeholder="点击选择市场"></td>
					</tr>
					<tr>
						<td>发布时间：</td>
						<td><input type="text" id="releaseTimeStart"
							onFocus="WdatePicker({onpicked:function(){releaseTimeStart.focus();},maxDate:'#F{$dp.$D(\'releaseTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){releaseTimeStart.focus();},maxDate:'#F{$dp.$D(\'releaseTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="releaseTimeStart" placeholder="开始时间"
							class="easyui-validatebox" style="width: 150px"></td>
						<td>~</td>
						<td><input type="text" id="releaseTimeEnd"
							onFocus="WdatePicker({onpicked:function(){releaseTimeStart.focus();},maxDate:'#F{$dp.$D(\'releaseTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){releaseTimeStart.focus();},maxDate:'#F{$dp.$D(\'releaseTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="releaseTimeEnd" placeholder="结束时间"
							class="easyui-validatebox" style="width: 150px"></td>
						<td>提交时间：</td>
						<td><input type="text" id="createTimeStart"
							onFocus="WdatePicker({onpicked:function(){createTimeStart.focus();},maxDate:'#F{$dp.$D(\'createTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){createTimeStart.focus();},maxDate:'#F{$dp.$D(\'createTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="createTimeStart" placeholder="开始时间"
							class="easyui-validatebox" style="width: 150px"></td>
						<td>~</td>
						<td><input type="text" id="createTimeEnd"
							onFocus="WdatePicker({onpicked:function(){createTimeStart.focus();},maxDate:'#F{$dp.$D(\'createTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){createTimeStart.focus();},maxDate:'#F{$dp.$D(\'createTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="createTimeEnd" placeholder="结束时间"
							class="easyui-validatebox" style="width: 150px"></td>
						<td><a class="easyui-linkbutton icon-search-btn"
							iconCls="icon-search" id="btn-search">查询</a></td>
						<td></td>
					</tr>
				</table>
					<gd:btn btncode='BTNNPSXXXJGL01'>
					<input type="hidden" id="hdCheck"  value="check"/>
				</gd:btn>
				<gd:btn btncode='BTNNPSXXXJGL02'>
					<input type="hidden" id="hdView"  value="view"/>
				</gd:btn>
			</div>
		</form>
		<div style="margin-bottom: 5px">
			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/npsPurchase/main.js"></script>
</html>