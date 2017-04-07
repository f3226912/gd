<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="marketdg" title="">
	</table>
	<div id="markettb" style="padding:5px;height:auto">
		<form id="marketSearchForm" method="post">
		<div>
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
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
			{field:'id', title:'', width:20, hidden:true },
			{field:'marketName', title:'市场名称', width:150, align:'left',halign:'center'},
			{field:'combine', title:'所在地区', width:150, align:'left', halign:'center',formatter : regionFormatter},
			{field:'opt',title:'选择', width:60, align:'center', halign:'center',formatter:optselectformat}
		 
		]]
		
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

function optselectformat(value,row,index){
	var options = "<a class='operate' href='javascript:void(0);' onclick=select('" + row.id + "','" + row.marketName + "','" + index + "')>选择</a>";
	return options;
}

function select(id, marketName, index){
	var row = { "id" : id, "marketName" : marketName} ;
	marketSelectCallBackForList(index,row);
	$("#marketSelectDialog").dialog('destroy');
}

//列表查询先把市场的回调
function marketSelectCallBackForList(index, row){
	$("#chooseMarket").val(row.marketName);
}

$(document).ready(function(){

	var loadUrl = CONTEXT+'npsPurchase/getListOfMarket' ;
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


