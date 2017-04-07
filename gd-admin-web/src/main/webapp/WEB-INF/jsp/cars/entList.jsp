<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="entdg" title="">
	</table>
	<div id="enttb" style="padding:5px;height:auto">
	<form id="searchForm" method="post">
		<div>
		           企业名称:
			<input  type="text" id="companyName"  name="companyName"  style="width:150px" >&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-car-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='car-btn-reset' onclick="$('#searchForm')[0].reset();">重置</a>
		</div>
	</form>
	</div>

<script type="text/javascript">


function loadCarData(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#entdg').datagrid({
		url: loadUrl,
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#enttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
					{field:'memberId',title:'',width:70,checkbox:true},
					{field:'companyName',title:'企业名称',width:100,align:'center'},
					{field:'companyContact',title:'企业联系人',width:100,align:'center'},
					{field:'companyMobile',title:'企业联系人手机',width:100,align:'center'}
				]],
		onDblClickRow: function(index,row){
			carSelectCallBack(index,row);
		} 
	}); 
	//分页加载
	$("#enttb").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

var oldPhone =$("#addForm #phone").val();
var oldName = $("#memberName").html();
function carSelectCallBack(index,row){
	/* var mobile = $("#addForm #phone").val();
	if(mobile == ''){
		$("#addForm #phone").val(row.companyMobile);
		$("#memberName").html(row.companyContact);
	} */
	if(row.companyMobile !=oldPhone)
	{
		$("#addForm #phone").val(row.companyMobile);
	}
	if(row.companyContact !=oldName)
	{
		$("#memberName").html(row.companyContact);
	}
	$("#memberId").val(row.memberId);
	$("#companyName").val(row.companyName);
	$("#userMobile").val(row.companyMobile);
	
	$('#entDialog').dialog('close');
}

//查询按钮,根据name查询
$('#icon-search-car-select').click(function(){ 
	var params = {
	 "companyName" : $("#companyName").val()
	};
	loadCarData(params, CONTEXT+'cars/queryEnt');
});	

loadCarData( {companyName: ''}, CONTEXT+'cars/queryEnt');
</script>


