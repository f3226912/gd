<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>合作社认证管理</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="datagrid-table" title=""></table>
<div id="datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="datagrid-form" method="post">
	<div>
		申请时间：
		<input  type="text"  id="startDate" name="startDate"
			onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
			onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
			style="width:150px" >~
		<input  type="text"    id="endDate" name="endDate"
			onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"
			onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
		用户账号：
		<input type="text" id="account" name="account" placeholder="" class="easyui-validatebox" style="width:150px" >
		电话号码：
		<input type="text" id="mobile" name="mobile" placeholder="" class="easyui-validatebox" style="width:150px" >
		<br>
		合作社名称：
		<input type="text" id="corpName" name="corpName" placeholder="" class="easyui-validatebox" style="width:150px" >
		认证状态：
		<input id="certifStatus" style="width:120px" panelHeight="100" class="easyui-combobox" data-options="editable:false">
	</div>
	</form>
	<div style="margin-bottom:5px">
		<!-- <a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="btn-add" >新增</a> -->
		<!-- <a class="easyui-linkbutton icon-add-btn" iconCls="icon-edit" id="btn-edit" >编辑</a> -->
		<!-- <a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="btn-remove">删除</a> -->
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
	</div>
</div>
</body>
<script type="text/javascript">

var disableExport = false ;


$(function(){
	gudeng.certif.corp.initList();
	//查询按钮
	$('#btn-search').click(function(){
		gudeng.certif.corp.query();
	});

	//刷新按钮
	$('#btn-refresh').click(function(){
		gudeng.certif.corp.refresh();
	});

	//重置按钮
	$('#btn-reset').click(function(){
		gudeng.certif.corp.reset();
	});


	// 修改
	$('#btn-edit').click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		} else if($(row).length == 1){
			edit(getSelections("id"));
		} else if($(row).length > 1){
			warningMessage("请不要选择多条数据！");
			return false;
		}
	});

	// 删除操作
	$("#btn-remove").click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}
		delete(getSelections("id"));
	});

	$('#certifStatus').combobox({
		valueField:'keyString',
		textField:'valueString',
		url: CONTEXT+'certifCommon/loadStatus',
		editable:false,
		onSelect:function(record){
			$('#certifStatus').val(record.keyString);
		},
		onLoadSuccess : function(){
			var data = $('#certifStatus').combobox('getData');
			$('#certifStatus').combobox('select', data[0].valueString);
		}
	});

	/***数据导出功能***/
	$("#exportData").click(function(){

		var params = {
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"account" : $("#account").val(),
				"corpName" : $("#corpName").val(),
				"certifStatus" : $("#certifStatus").val()
			};
		var paramList = gudeng.commons.convertParamsToDelimitedList(params);

		$.ajax({
			url: CONTEXT+'certifCorp/checkExportParams',
			data : params,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'certifCorp/exportData',paramList,'post' );
					}else{
						slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
					}
				}else{
					warningMessage(data.message);
				}
			},
			error : function(data){
				warningMessage("error : " + data);
			}
		});
	});/***数据导出功能 -- end ***/

});

</script>
<script type="text/javascript" src="${CONTEXT}js/certifCorp/main.js"></script>
</html>