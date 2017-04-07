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
		<meta http-equiv="keywords" content="农批网,公告信息,谷登农批网公告"/>
		<meta http-equiv="description" content="谷登农批网重大信息公告中心！"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>农批商订单信息_谷登农批网</title>
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
	</head>
<body>
	<table id="orderdg" title="">
	</table>
	<div id="ordertb" style="padding:5px;height:auto">
		<form id="orderSearchForm" method="post">
		<div>
			<input type="hidden" id="typeList" name="type" value="${type}" >
			订单编号 :
			<input type="text" id="orderNoList" class="easyui-validatebox" name="orderNo" style="width:120px" >
			第三方支付流水 :
			<input type="text" id="thirdStatementIdList" class="easyui-validatebox" name="thirdStatementId" style="width:120px" >
			用户账号 :
			<input type="text" id="accountList" class="easyui-validatebox" name="account" style="width:120px" >
			手机号码 :
			<input type="text" id="mobile" class="easyui-validatebox" name="mobile" style="width:120px" >
			订单状态 :
			<select name="orderStatus" id="orderStatusList" style="width: 120px;">
				<option value="">---请选择---</option>
				<option value="1">待付款</option>
				<option value="3">已付款</option>
				<option value="8">已关闭</option>
				<!-- <option value="9">已过期</option> -->
			</select>
			<br/>
			创建时间 :
			<input  type="text"  id="startDate" name="startDate"
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:120px" >~
			<input  type="text"    id="endDate" name="endDate"
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:120px">
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='icon-reload'>重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</div>
		<div id="editDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<div id="editButtondiv" style="display:inline-block">
		        	<gd:btn btncode='BTNDDGLQBDD06'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveObj('1')">提交</a></gd:btn>
<%-- 		        	<gd:btn btncode='BTNDDGLQBDD07'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveObj('2')">付款驳回</a></gd:btn> --%>
	        	</div>
<!-- 	        	<div id="updateButtondiv" style="display:inline-block"> -->
<%-- 					<gd:btn btncode='BTNDDGLQBDD08'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveObj('3')">修改流水号</a></gd:btn> --%>
<!-- 	        	</div> -->
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
	
	<!-- 编辑订单 -->
	<div id="editDialog2" class="easyui-dialog"  closed="true" modal="true">
		<div style="margin-left:20px;margin-top:20px;width:300px;">
		<span style="font-weight:bold;">订单信息</span><br>
		<table width="100%" border="0" class="comtable5">
			<tr>
				<td>订单编号:</td>
				<td><input name="orderNo" style="border-width:0"/></td>
			</tr>
			<tr>
				<td><span style="color:red">*</span>发票号码:</td>
				<td><input name="invoiceContent" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
			</tr>
		</table>
		</div>
		<div style="text-align:center;margin-top:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#editDialog2').dialog('close')">取消</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:editDialog2Save()">保存</a>
		</div>
	</div>
</body>
<script type="text/javascript">
function orderNoformat(value,row,index){
	var html="<gd:btn btncode='BTNDDGLQBDD01'><a class='operate' href='javascript:;' onclick=editObj('"+row.persaleId+"');>"+value+"</a>&nbsp;</gd:btn>";
	if(html==''){
		return value;
	}else{
		return html;
	}
}

function optformat(value,row,index){
	// 待付款，已关闭添加编辑按钮，2016/9/1 补单需求
	var edit = "";
	if(row.orderStatus =="3" && row.invoice == ''){
		edit ="<a class='operate' href='javascript:;' onclick=editObj2('"+row.persaleId+"','"+row.orderNo+"');>操作</a>&nbsp;";
	}
	edit = "<span style='width:30px;display:inline-block'>"+edit+"</span>&nbsp;";
	return edit+"<span style='width:30px;display:inline-block'><a class='operate' href='javascript:;' onclick=editObj('"+row.persaleId+"');>详情</a>&nbsp;</span>";
}

var disableExport = false ;
</script>
<script type="text/javascript" src="${CONTEXT}js/order/serviceorder.js"></script>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
</html>