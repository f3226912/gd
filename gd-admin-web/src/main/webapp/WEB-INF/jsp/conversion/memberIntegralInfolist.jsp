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
		<title>member</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="conversiondg" title="">
	</table>
	<div id="conversiontb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
			注册账号: <input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			用户类型：
			<select name="userType" id="userType">
				<option value="">请选择</option>
				<option value="1">个人</option>
				<option value="2">企业</option>
			</select>
			<gd:btn btncode='BTNLPGLHYJFDHLW01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</form>
	</div>
	<div id="giftDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMarket">
			<div id="#dlg-buttonsMarket" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-gift-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#giftDialog').dialog('close')">关闭</a>
	        </div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNLPGLHYJFDHLW02'><a class='operate' href='javascript:;' onclick=showGiftList('"+row.memberId+"','"+row.integral+"');>兑换礼物</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/conversion/main.js"></script>
</html>

