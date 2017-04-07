<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
	<table id="marketdg" title="">
	</table>
	<div id="markettb" style="padding:5px;height:auto">
		<form id="marketSearchForm" method="post">
		<div>
		          街市 名称: 
			<input  type="text" id="marketName"  name="marketName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-market-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='location.reload(true)'">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick='location.reload(true)'">刷新</a>
		</div>
		</form>
		
	</div>

<script type="text/javascript">

function loadMarketData(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#marketdg').datagrid({
		url: loadUrl,
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#markettb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
			{field:'id',title:'',width:80,checkbox:true},
			{field:'marketName',title:'街市名称',width:100,align:'center'},
			{field:'provinceName',title:'省份',width:100,align:'center'},
			{field:'cityName',title:'城市',width:100,align:'center'},
			{field:'areaName',title:'区域',width:100,align:'center'},
			{field:'useStatus',title:'状态',width:100,align:'center'},
			{field:'address',title:'详细地址',width:100,align:'center'},
			{field:'description',title:'说明',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
			}}
		]],
		onDblClickRow: function(index,row){
			$("#market_add").val(row.id);
			$("#showMarketWin").val(row.marketName);
			$('#marketDialog').dialog('close');
		}
	}); 
	//分页加载
	$("#marketdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$(document).ready(function(){

	loadMarketData(null, CONTEXT+'market/query');
	// 查询按钮,根据name查询
	$('#icon-search-market-select').click(function(){ 
		var params = {
/* 			"name" : $('#marketSearchForm #marketName').val(),
			"startDate":$('#marketSearchForm #startDate').val(),
			"endDate":$('#marketSearchForm #endDate').val() */
		}
		loadMarketData(params, CONTEXT+'market/queryByName');
	});	
});
</script>


