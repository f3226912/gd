<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/head.inc"%>
<%@ include file="../pub/tags.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="农批网,公告信息,谷登农批网公告" />
<meta http-equiv="description" content="谷登农批网重大信息公告中心！" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible"
	content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<title>鲜农订单信息_谷登农批网</title>
<link rel="stylesheet" href="${CONTEXT}css/admin-order.css" />
</head>
<body>
	<table id="orderdg" title="">
	</table>
	<div id="ordertb" style="padding: 5px; height: auto">
		<form id="orderSearchForm" method="post">
			<div>
				<table>
					<tr>
						<td>订单编号 :</td>
						<td><input type="text" id="txtOrderNo"
							class="easyui-validatebox" name="orderNo" style="width: 120px"
							maxlength="50"></td>
						<td>手机号码 :</td>
						<td><input type="text" id="txtMobile"
							class="easyui-validatebox" name="mobile" style="width: 120px"
							maxlength="20"></td>

						<td>买家姓名 :</td>
						<td><input type="text" id="txtRealName"
							class="easyui-validatebox" name="realName" style="width: 120px"
							maxlength="30"></td>
						<td>订单状态 :</td>
						<td><select name="orderStatus" id="sltOrderStatus"
							style="width: 120px;">
								<option value="">---请选择---</option>
								<option value="1">待付款</option>
								<option value="3">已付款</option>
								<option value="8">已关闭</option>
								<!-- <option value="9">已过期</option> -->
						</select></td>
					<tr>
						<td>创建时间 :</td>
						<td colspan="7"><input type="text" id="startDate"
							name="startDate"
							onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px">~ <input type="text" id="endDate"
							name="endDate"
							onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px"></td>
					</tr>
				</table>
			</div>
		</form>
		<div style="margin-bottom: 5px">
			<gd:btn btncode='BTNDDGLXNDD01'>
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search"
					id="icon-search">查询</a>&nbsp;&nbsp;</gd:btn>

			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload"
				id='icon-reload'>重置</a>&nbsp;&nbsp; <a class="easyui-linkbutton"
				iconCls="icon-reload" id="icon-refresh">刷新</a>&nbsp;&nbsp;
			<gd:btn btncode='BTNDDGLQBDD04'>
				<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
			</gd:btn>
		</div>
		<div id="editDialog" class="easyui-dialog"
			style="width: 800px; height: 500px;" closed="true" modal="true"
			buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align: center">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
	function orderNoformat(value, row, index) {
		var html = "<a class='operate' href='javascript:;' onclick=editObj('"
				+ row.persaleId + "');>" + value + "</a>&nbsp;";
		if (html == '') {
			return value;
		} else {
			return html;
		}
	}

	function optformat(value, row, index) {
		var edit = "";
		return edit
				+ "<span style='width:30px;display:inline-block'><a class='operate' href='javascript:;' onclick=editObj('"
				+ row.persaleId + "');>详情</a>&nbsp;</span>";
	}

	var disableExport = false;
</script>
<script type="text/javascript" src="${CONTEXT}js/order/sendnoworder.js"></script>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
</html>