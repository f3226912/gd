<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<table id="datasourcedetaildg" title="">
	</table>
	<div id="datasourcedetailtb" style="padding:5px;height:auto">
		<div>
			数据源名称:${dto.name}    &nbsp;&nbsp;&nbsp;&nbsp;数据最后更新时间:${dto.lastUpdateTimeStr }
			<br>
			<!-- <a class="easyui-linkbutton icon-search-btn" iconCls="icon-save" id="icon-dowloadData">模版下载</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-save" id="icon-importData">导入数据</a> -->
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-save" id="icon-exportData">导出数据</a>
		</div>
	</div>
	<div id='Loading' style="position: absolute;opacity: 0.5;filter:Alpha(opacity=50); z-index: 10000; top: 0px; left: 0px;  
        width: 100%; height: 100%; background: gray; text-align: center;display: none;">  
        <h1 style="top: 48%; position: relative;">  
            <font color="#15428B">导入中,请稍候···</font>  
        </h1>  
    </div>

<script type="text/javascript">

function loadProList(){
	//数据加载
	$('#datasourcedetaildg').datagrid({
		url:CONTEXT+'datasource/queryProOperate',
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
			{field:'datatimes',title:'数据更新时间',width:100,align:'center'},
			{field:'comRegCount',title:'当日平台注册用户数',width:100,align:'center'},
			{field:'sumComRegCount',title:'累计平台注册用户数',width:100,align:'center'},
			{field:'growthRate',title:'环比增长率',width:100,align:'center'}
		]]
	});
	//分页加载
	//$("#datasourcedetaildg").datagrid("getPager").pagination({
	//	pageList: [10,20,50,100]
	//});
}

$(document).ready(function(){
	loadProList();
	$("#icon-exportData").click(function(){
		window.location.href = CONTEXT + 'datasource/exportProOperate';
	});
});
</script>

