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
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
		<c:if test="${type==1 }">
			<title>皮重入场登记</title>
		</c:if>
		<c:if test="${type==2 }">
			<title>总重出场登记</title>
		</c:if>
		<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>

	</head>
<body>

	<table id="buyerOuterdg" title="">
	</table>
	<div id="buyerOutertb" style="padding:5px;height:auto">
		<form id="buyerOuterSearchForm" method="post" action="">
		<input type="hidden" name="type" value="${type }" />
		<div>
			<c:if test="${type == '2' }">
				订单编号: <input  type="text" id="orderNo" name="orderNo" style="width:150px" maxlength="15">&nbsp;
			</c:if>
			用户账号: <input  type="text" id="account" name="account" style="width:150px" maxlength="20">&nbsp;
			车辆车牌: <input  type="text" id="carNumber" name="carNumber" style="width:150px" maxlength="20">&nbsp;
			<br/>
			皮重登记时间 :
			<input  type="text"  id="tareStartTime" name="tareStartTime"
				onFocus="WdatePicker({onpicked:function(){tareStartTime.focus();},maxDate:'#F{$dp.$D(\'tareEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){tareStartTime.focus();},maxDate:'#F{$dp.$D(\'tareEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:150px" >~
			<input  type="text"    id="tareEndTime" name="tareEndTime"
				onFocus="WdatePicker({onpicked:function(){tareEndTime.focus();},minDate:'#F{$dp.$D(\'tareStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){tareEndTime.focus();},minDate:'#F{$dp.$D(\'tareStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px">
			总重登记时间 :
			<input  type="text"  id="totalStartTime" name="totalStartTime"
				onFocus="WdatePicker({onpicked:function(){totalStartTime.focus();},maxDate:'#F{$dp.$D(\'totalEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){totalStartTime.focus();},maxDate:'#F{$dp.$D(\'totalEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width:150px" >~
			<input  type="text"    id="totalEndTime" name="totalEndTime"
				onFocus="WdatePicker({onpicked:function(){totalEndTime.focus();},minDate:'#F{$dp.$D(\'totalStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				onClick="WdatePicker({onpicked:function(){totalEndTime.focus();},minDate:'#F{$dp.$D(\'totalStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px">
		<br/>
		状态 &nbsp; :
		<select id="tapWeight" name="tapWeight" value="" class="easyui-combobox">
				<option value="-1">请选择</option>
				<option value="4">称重完成</option>
				<option value="1">皮重登记</option>
				<option value="1">总重登记</option>
	      </select>
	     </div>
	     </form>
			<gd:btn btncode='BTNMGGLCGSPZRCDJ03'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="serachList">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="reloadList">重置</a>
			<gd:btn btncode='BTNMGGLCGSPZRCDJ02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>


	</div>
	<div id="showDialog" class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttonsEdit">
		<div id="dlg-buttonsEdit" style="text-align:center">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addCarNumber();">保存</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="$('#showDialog').dialog('close');">关闭</a>
	    </div>
	</div>
	<script type="text/javascript">
	var type = ${type };
	function optformat(value,row,index){
		return "<gd:btn btncode='BTNMGGLCGSPZRCDJ01'><a class='operate' href='javascript:;' onclick=showDetail('"+row.weighCarId+"');>详细</a></gd:btn>";
	}
	var disableExport = false ;
	</script>
	<script type="text/javascript" src="${CONTEXT}js/outer/buyer.js"></script>
</body>
</html>

