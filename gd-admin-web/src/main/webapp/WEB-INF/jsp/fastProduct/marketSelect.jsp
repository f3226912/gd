<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="marketdg" title="">
	</table>
	<div id="markettb" style="padding:5px;height:auto">
		<form id="marketSearchForm" method="post">
		<div>
			<input type="hidden" id="callback" value="${callback}" />
		          市场名称&nbsp;: 
			<input  type="text" id="marketName"  name="marketName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-market-select"  >查询</a>&nbsp;
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
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
			{field:'id', title:'', width:80, hidden:true },
			{field:'marketName', title:'市场名称', width:100, align:'center'},
			{field:'combine', title:'所在地区', width:100, align:'center', formatter : regionFormatter},
			{field:'opt',title:'选择', width:100, align:'center', formatter:optformat}
		 
		]],
		onDblClickRow: function(index,row){
			var type = $("#callback").val();
			if (type == 1){
				marketSelectCallBack(index,row);
			}else{
				marketSelectCallBackForList(index,row);
			}
			$("#marketSelectDialog").dialog('destroy');
		}
	}); 
	//分页加载
	$("#marketdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function regionFormatter(value, row, index){
	var provinceName = row.provinceName ? row.provinceName : "" ;
	var cityName = row.cityName ? row.cityName : "";
	var areaName = row.areaName ? row.areaName : "";
	var combine = provinceName + cityName + areaName;
	return combine ;
}

function optformat(value,row,index){
	var options = "<a class='operate' href='javascript:void(0);' onclick=select('" + row.id + "','" + row.marketName + "','" + index + "')>选择</a>";
	return options;
}

function select(id, marketName, index){
	var row = { "id" : id, "marketName" : marketName} ;
	var type = $("#callback").val();
	if (type == 1){
		marketSelectCallBack(index,row);
	}else{
		marketSelectCallBackForList(index,row);
	}
	$("#marketSelectDialog").dialog('destroy');
}

$(document).ready(function(){

	var loadUrl = CONTEXT+'fastProduct/getListOfMarket' ;
	loadMarketData(null, loadUrl);
	
	// 查询按钮,根据marketName查询
	$('#icon-search-market-select').click(function(){ 
		var params = {
 				"marketName" : $("#marketName").val()
 			}
		loadMarketData(params, loadUrl);
	});
	
});

</script>


