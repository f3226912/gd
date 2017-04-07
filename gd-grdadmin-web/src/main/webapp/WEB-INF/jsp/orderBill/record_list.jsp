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
		<title>POS刷卡银行流水管理</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="posPayBilldg" title="">
	</table>
	<div id="posPayBilltb" style="padding:5px;height:auto">
		<form id="posPayBillSearchForm" method="post">
			<input type="hidden" id="typeList" name="type" value="${type}" >
			<c:if test="${type != 2 && type != 3}">
			订单编号  :   <input  type="text" id=OrderNo    class="easyui-validatebox"    name="OrderNo" style="width:150px" >
			</c:if>
			终端号  :     <input  type="text" id=clientNo    class="easyui-validatebox"    name="clientNo" style="width:150px" ></br>
			<c:if test="${type != 2 && type != 3}">
			商铺名称  :   <input  type="text" id=shopName    class="easyui-validatebox"    name="shopName" style="width:150px" >
			</c:if>			
			<c:if test="${type != 4}">
			系统参考号  : <input  type="text" id="sysRefeNo"    class="easyui-validatebox"    name="sysRefeNo" style="width:150px"></br>
			银行交易时间区间 :
			<input  type="text"  id="billBeginTime" name="billBeginTime"  
				onFocus="WdatePicker({onpicked:function(){billBeginTime.focus();},maxDate:'#F{$dp.$D(\'billEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){billBeginTime.focus();},maxDate:'#F{$dp.$D(\'billEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="billEndTime" name="billEndTime"   
				onFocus="WdatePicker({onpicked:function(){billBeginTime.focus();},minDate:'#F{$dp.$D(\'billBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){billBeginTime.focus();},minDate:'#F{$dp.$D(\'billBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			</br>
			交易金额：<input  type="text" id=tradeMoney    class="easyui-validatebox"   name="tradeMoney">元
			</c:if>
			<a class="easyui-linkbutton" iconCls="icon-save" id="exportData">导出</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#posPayBillSearchForm').form('validate');">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</form>
		<div id="orderDetailDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#orderDetailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
	<div id="posPayBillListDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#orderBillList">
		<div id="orderBillList" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#posPayBillListDialog').dialog('close')">关闭</a>
	    </div>
	</div>
</body>
<script type="text/javascript">
function orderNoformat(value,row,index){
	if(value == null || value==undefined || value == ''){
		return '';
	}else{
		var html="<gd:btn btncode='BTNDDGLQBDD01'><a class='operate' href='javascript:;' onclick=editObj('"+row.persaleId+"');>"+value+"</a>&nbsp;</gd:btn>";
		return html;
	}
}
</script>
<script type="text/javascript" src="${CONTEXT}js/orderbill/record.js"></script>
</html>