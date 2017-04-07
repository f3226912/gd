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
		<meta http-equiv="keywords" content="农批网,消息,谷登农批网消息"/>
		<meta http-equiv="description" content="谷登农批网消息中心！"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>消息_谷登农批网</title>
	</head>
<body>
	<table id="noticedg" title="">
	</table>
	<div id="noticetb" style="padding:5px;height:auto">
		<form id="noticeSearchForm" method="post">
		<div>
			消息名称: 
			<input type="text" id="title" class="easyui-validatebox" name="title" style="width:150px" >
			<!-- 发布时间 :
			<input  type="text"  id="startDate" name="startDate"  
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="endDate" name="endDate"   
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px"> -->
			消息发送对象:
			<select name="client" id="client" style="width: 150px;">
				<option value="">---请选择---</option>
				
				<option value="1">农批商</option>
				<!-- <option value="农批友">农批友</option> -->
				<option value="2">供应商</option>
				<option value="3">农速通</option>
				<option value="4">农商友</option>
				<option value="5">谷登农批网</option>
				
			</select>
			发送状态:
			<select name="state" id="state" style="width: 150px;">
				<option value="">---请选择---</option>
				<option value="2">待发送</option>
				<option value="1">已发送</option>
				
			</select>
			<gd:btn btncode='BTNGGGLGOGGL05'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='icon-reload'>重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNGGGLGOGGL01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >创建</a></gd:btn>
			<!--<gd:btn btncode='BTNGGGLGOGGL02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>-->
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:800px;height:430px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveadd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close');">关闭</a>
	        </div>
		</div>
		 
		<div id="editDialog" class="easyui-dialog" style="width:800px;height:430px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveedit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		 
		<div id="detailDialog" class="easyui-dialog" style="width:800px;height:430px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="#dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	var operateHtml ="";
	if(row.state == "2"){
		operateHtml = "<gd:btn btncode='BTNGGGLGOGGL02'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>发送</a></gd:btn>&nbsp;";
	}
	operateHtml += "<gd:btn btncode='BTNGGGLGOGGL04'><a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>详情</a></gd:btn>&nbsp;";
	
	if(row.state == "2"){
		operateHtml += "<gd:btn btncode='BTNGGGLGOGGL04'><a class='operate' href='javascript:;' onclick=updateObj('"+row.id+"');>编辑</a></gd:btn>";
	}
	
	return operateHtml;
}
</script>
<script type="text/javascript" src="${CONTEXT}js/push/notice.js"></script>
 
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
 
<script type="text/javascript">

$(document).ready(function(){
	initList();
	
	
	//查询按钮
	$('#icon-search').click(function(){ 
		var params = {
			
			"client" : $("#client").val(),
			"title" : $("#title").val(),
			"state" : $("#state").val()
			};
		loadList(params);
	});
	
	//新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'编辑消息','href':CONTEXT+'push/noticeAdd', 'width': 800,'height': 430}).dialog('open');
	});
	<!--
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#noticeSearchForm')[0].reset();
		$("#noticedg").datagrid('load', {});
	});
	-->
	//重置按钮
	$('#icon-reload').click(function(){
		$('#noticeSearchForm')[0].reset();
	});
});
</script>
</html>