<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<table id="datasourcedetaildg" title="">
	</table>
	<div id="datasourcedetailtb" style="padding:5px;height:auto">
		<div>
			数据源名称:${dto.name}    &nbsp;&nbsp;&nbsp;&nbsp;数据最后更新时间:${dto.lastUpdateTimeStr }
			<br>
			<!-- <a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search2">模版下载</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search2">导入数据</a> -->
			<a class="easyui-linkbutton icon-save-btn" iconCls="icon-save" id="icon-exportData">导出数据</a>
		</div>
	</div>

<script type="text/javascript">

function loadProList(){
	//数据加载
	$('#datasourcedetaildg').datagrid({
		url:CONTEXT+'datasource/queryOrderBill',
		//width: 1000,  
		queryParams: '',
		height: 'auto', 
		nowrap:true,
		toolbar:'#datasourcedetailtb',
		pageSize:10,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'businessNo',title:'商户编号',width:100,align:'center'},
			{field:'businessName',title:'商户名称',width:100,align:'center'},
			{field:'tradeType',title:'交易类型',width:100,align:'center'},
			{field:'tradeDay',title:'交易日期',width:100,align:'center'},
			{field:'cardNo',title:'交易卡号',width:100,align:'center'},
			{field:'clientNo',title:'终端号',width:100,align:'center'},
			{field:'tradeMoney',title:'交易金额',width:100,align:'center'},
			{field:'sysRefeNo',title:'系统参考号',width:100,align:'center'},
			{field:'marketId',title:'所属市场',width:100,align:'center',formatter:formatterMarket}
		]]
	});
	//分页加载
	//$("#datasourcedetaildg").datagrid("getPager").pagination({
	//	pageList: [10,20,50,100]
	//});
}

function formatterMarket(val, row) {
	return "武汉白沙洲批发市场";
}

$(document).ready(function(){
	loadProList();
	/***数据导出功能***/
	$("#icon-exportData").click(function(){
		window.location.href = CONTEXT + 'datasource/exportOrderBill';
	});
});
</script>

