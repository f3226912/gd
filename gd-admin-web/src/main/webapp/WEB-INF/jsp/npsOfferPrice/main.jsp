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
<title>供应商报价管理</title>
</head>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="datagrid-table" title=""></table>
	<div id="datagrid-tool-bar" style="padding: 5px; height: auto">
		<form id="datagrid-form" method="post">
			<table>
				<tr>
					<td>手机号码：</td>
					<td><input type="text" id="mobilePhone" name="mobilePhone"
						placeholder="手机号码" class="easyui-validatebox"
						style="width: 150px"></td>
					<td>询价信息编号：</td>
					<td><input type="text" id="purchaseId" name="purchaseId"
						placeholder="询价信息编号" class="easyui-validatebox"
						style="width: 150px"></td>
					<td>商品名称：</td>
					<td><input type="text" id="goodsName" name="goodsName"
						placeholder="商品名称" class="easyui-validatebox"
						style="width: 150px"></td>
					<!--  <td>询价状态：</td>
					<td><select id="purchaseStatus" name="purchaseStatus">
							<option value="">-请选择-</option>
							<option value="1">待审核</option>
							<option value="2">审核通过</option>
							<option value="3">已驳回</option>
							<option value="4">用户撤销</option>
							<option value="6">已结束</option>
							</select></td>
							-->
					<td></td>
				</tr>
				<tr>
					<td>报价时间：</td>
					<td><input type="text" id="offerTimeStart"
							onFocus="WdatePicker({onpicked:function(){offerTimeStart.focus();},maxDate:'#F{$dp.$D(\'offerTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){offerTimeStart.focus();},maxDate:'#F{$dp.$D(\'offerTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="offerTimeStart" placeholder="开始时间"
							class="easyui-validatebox" style="width: 150px"></td>
					<td colspan="2">~<input type="text" id="offerTimeEnd"
							onFocus="WdatePicker({onpicked:function(){offerTimeStart.focus();},maxDate:'#F{$dp.$D(\'offerTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){offerTimeStart.focus();},maxDate:'#F{$dp.$D(\'offerTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="offerTimeEnd" placeholder="结束时间"
							class="easyui-validatebox" style="width: 150px"></td>
					<td>报价状态：</td>
					<td><select name="status" id="status">
							<option value="">-请选择-</option>
							<option value="1">显示</option>
							<option value="3">隐藏</option>
							</select></td>
					<td><a class="easyui-linkbutton icon-search-btn"
							iconCls="icon-search" id="btn-search">查询</a></td>
				</tr>
			</table>
				<gd:btn btncode='BTNGYSXXBJGL01'>
					<input type="hidden" id="hdEdit"  value="edit"/>
				</gd:btn>
		</form>
		<div style="margin-bottom: 5px">
			 <a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var disableExport = false;
</script>
<script type="text/javascript" src="${CONTEXT}js/npsOfferPrice/main.js"></script>
