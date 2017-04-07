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
	<table id="entranceWeighdg" title="">
	</table>
	<div id="entranceWeightb" style="padding:5px;height:auto">
		<form id="entranceWeighSearchForm" method="post">
			<input type="hidden" name="state" id="state" value="${state}">
			<input type="hidden" name="status" id="status" value="${status}">
			用户账号: <input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			出场车辆车牌: <input  type="text" id="carNumber"    class="easyui-validatebox"    name="carNumber" style="width:150px" ><br>
			皮重登记时间:
			<input  type="text"  id="tareStartTime" name="tareStartTime"  
				onFocus="WdatePicker({onpicked:function(){tareStartTime.focus();},maxDate:'#F{$dp.$D(\'tareEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){tareStartTime.focus();},maxDate:'#F{$dp.$D(\'tareEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="tareEndTime" name="tareEndTime"   
				onFocus="WdatePicker({onpicked:function(){tareEndTime.focus();},minDate:'#F{$dp.$D(\'tareStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){tareEndTime.focus();},minDate:'#F{$dp.$D(\'tareStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			总重登记时间 :
			<input  type="text"  id="totalStartTime" name="totalStartTime"  
				onFocus="WdatePicker({onpicked:function(){totalStartTime.focus();},maxDate:'#F{$dp.$D(\'totalEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){totalStartTime.focus();},maxDate:'#F{$dp.$D(\'totalEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="totalEndTime" name="totalEndTime"   
				onFocus="WdatePicker({onpicked:function(){totalEndTime.focus();},minDate:'#F{$dp.$D(\'totalStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){totalEndTime.focus();},minDate:'#F{$dp.$D(\'totalStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px"><br>
			状态： <select name="tapWeight" id="tapWeight">
					<option value="">请选择</option>
					<option value="1">总重登记</option>
					<option value="2">皮重登记</option>
					<option value="4">称重完成</option>
				  </select>
			<gd:btn btncode='BTNMGGLHZRCGB03'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#entranceWeighSearchForm').form('validate');">查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<c:if test="${state=='1'}">
				<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-entranceWeighrefresh">刷新</a>
			</c:if>
			<c:if test="${state=='2'}">
				<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-outWeighrefresh">刷新</a>
			</c:if>
			<gd:btn btncode='BTNMGGLHZRCGB02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</form>
	</div>
	<div id="entranceWeighInfoDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-entranceWeighInfoDialogList">
			<div id="dlg-entranceWeighInfoDialogList" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#entranceWeighInfoDialog').dialog('close')">关闭</a>
	        </div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNMGGLHZRCGB01'><a class='operate' href='javascript:;' onclick=entranceWeighInfo('"+row.weighCarId+"',"+row.weigh+");>详情</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/shipper/main.js"></script>
</html>

