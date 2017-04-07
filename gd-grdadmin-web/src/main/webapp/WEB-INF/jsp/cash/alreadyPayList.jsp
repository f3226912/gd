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
		<title>cash</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="cashdg" title="">
	</table>
	<div id="cashtb" style="padding:5px;height:auto">
		<form id="cashSearchForm" method="post">
			<input type="hidden" name="status" id="status" value="${status }">
			提现会员账号: <input  type="text" id=account    class="easyui-validatebox"    name="account" style="width:150px" >
			姓名: <input  type="text" id=realName    class="easyui-validatebox"    name="realName" style="width:150px" >
			银行卡号:<input  type="text" id=bankCardNo    class="easyui-validatebox"    name="bankCardNo" style="width:150px" >
			开户行：<select name="isABC" id="isABC">
						<option value="-1">请选择</option>
						<option value='0'>农行</option>
						<option value="1">非农行</option>
				   </select><br/>
			申请时间 :
			<input  type="text"  id="applyBeginTime" name="applyBeginTime"  
				onFocus="WdatePicker({onpicked:function(){applyBeginTime.focus();},maxDate:'#F{$dp.$D(\'applyEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){applyBeginTime.focus();},maxDate:'#F{$dp.$D(\'applyEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="applyEndTime" name="applyEndTime"   
				onFocus="WdatePicker({onpicked:function(){applyEndTime.focus();},minDate:'#F{$dp.$D(\'applyBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){applyEndTime.focus();},minDate:'#F{$dp.$D(\'applyBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			结款时间 :
			<input  type="text"  id="payBeginTime" name="payBeginTime"  
				onFocus="WdatePicker({onpicked:function(){payBeginTime.focus();},maxDate:'#F{$dp.$D(\'payEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){payBeginTime.focus();},maxDate:'#F{$dp.$D(\'payEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="payEndTime" name="payEndTime"   
				onFocus="WdatePicker({onpicked:function(){payEndTime.focus();},minDate:'#F{$dp.$D(\'payBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){payEndTime.focus();},minDate:'#F{$dp.$D(\'payBeginTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px"> <br/>
			<gd:btn btncode='BTNTXGLYJKGL03'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#cashSearchForm').form('validate');">查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refreshalready">刷新</a>
			<gd:btn btncode='BTNTXGLYJKGL01'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</form>
	</div>
	<div id="accountFlowListDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsFlowList">
			<div id="dlg-buttonsFlowList" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#accountFlowListDialog').dialog('close')">关闭</a>
	        </div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	if(row.status=='0'){							
		return "<gd:btn btncode='BTNTXGLTXSQGL02'><a class='operate' href='javascript:;' onclick=pay('"+row.cashReqId+"');>提款</a></gd:btn>";
	}else if(row.status=='1'){
		return "";
	}
}
</script>
<script type="text/javascript" src="${CONTEXT}js/cash/main.js"></script>
</html>

