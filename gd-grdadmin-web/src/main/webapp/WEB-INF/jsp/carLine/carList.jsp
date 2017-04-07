<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="cardg" title="">
	</table>
	<div id="cartb" style="padding:5px;height:auto">
	<form id="carSearchForm" method="post">
		<div>
		           车牌号码:
			<input  type="text" id="carNumber"  name="carNumber"  style="width:150px" >&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-car-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='car-btn-reset' onclick="$('#carSearchForm')[0].reset();">重置</a>
		</div>
	</form>
	</div>

<script type="text/javascript">


function loadCarData(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#cardg').datagrid({
		url: loadUrl,
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#cartb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
					{field:'id',title:'',width:70,checkbox:true},
					{field:'carNumber',title:'车牌号码',width:100,align:'center'},
					{field:'carTypeName',title:'车辆类型',width:100,align:'center'},
					{field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
					{field:'carLength',title:'车长(米)',width:100,align:'center'},
					{field:'nickName',title:'联系人',width:100,align:'center'},
					{field:'phone',title:'电话号码',width:100,align:'center'}
					
				]],
		onDblClickRow: function(index,row){
			carSelectCallBack(index,row);
			

		} 
	}); 
	//分页加载
	$("#cartb").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function carSelectCallBack(index,row){
	$("#carId").val(row.id);
	$("#queryCarList").val(row.carNumber);
	$('#carDialog').dialog('close');
}

//查询按钮,根据name查询
$('#icon-search-car-select').click(function(){ 
	var params = {
	 "carNumber" : $("#carNumber").val()
	}
	loadCarData(params, CONTEXT+'carLine/queryCar');
});	

loadCarData( {carNumber: $("#carNumber").val()}, CONTEXT+'carLine/queryCar/');
</script>


