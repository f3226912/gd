<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="marketdg" title="">
	</table>
	<div id="markettb" style="padding:5px;height:auto">
	<!--	<form id="marketSearchForm" method="post">
		<div>
		    	 &nbsp;创建时间&nbsp;: 
		    <input  type="text"  id="market_startDate" name="market_startDate"  
				onFocus="WdatePicker({onpicked:function(){market_startDate.focus();},maxDate:'#F{$dp.$D(\'market_endDate\')}'})"   
				onClick="WdatePicker({onpicked:function(){market_startDate.focus();},maxDate:'#F{$dp.$D(\'market_endDate\')}'})"    
				style="width:150px" >~
			<input  type="text"    id="market_endDate" name="market_endDate"   
				onFocus="WdatePicker({onpicked:function(){market_endDate.focus();},minDate:'#F{$dp.$D(\'market_startDate\')}'})"   
				onClick="WdatePicker({onpicked:function(){market_endDate.focus();},minDate:'#F{$dp.$D(\'market_startDate\')}'})"   style="width:150px">
		          街市名称&nbsp;: 
			<input  type="text" id="marketName"  name="marketName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-market-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='$("#marketSearchForm").reset()'>重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick='location.reload(true)'">刷新</a>
		</div>
		</form> -->
		
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
		          
			{field:'id',title:'',width:80,hidden:true},
			{field:'marketName',title:'街市名称',width:100,align:'center'},
			{field:'provinceName',title:'省份',width:100,align:'center'},
			{field:'cityName',title:'城市',width:100,align:'center'},
			{field:'areaName',title:'区域',width:100,align:'center'},
			{field:'useStatus',title:'状态',width:100,align:'center'},
			{field:'address',title:'详细地址',width:100,align:'center'},
			{field:'description',title:'说明',width:100,align:'center'},
		 
		]],
		onDblClickRow: function(index,row){
			marketSelectCallBack(index,row);
		}
	}); 
	//分页加载
	$("#marketdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$(document).ready(function(){

	loadMarketData(null, CONTEXT+'market/queryByType/2');
	// 查询按钮,根据name查询
	$('#icon-search-market-select').click(function(){ 
		var params = {
 			"marketName" : $("#marketName").val(),
			"startDate":$("#market_startDate").val(),
			"endDate":$("#market_endDate").val() 
		}
		loadMarketData(params, CONTEXT+'product/queryByType/2');
	});	
});

</script>


