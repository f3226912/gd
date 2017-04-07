<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="entdg" title="">
	</table>
	<div id="enttb" style="padding:5px;height:auto">
	<form id="searchForm" method="post">
		<div>
		           企业名称:
			<input  type="text" id="entName"  name="entName"  style="width:150px" >&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; 
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-car-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='car-btn-reset' onclick="$('#searchForm')[0].reset();">重置</a>
		</div>
	</form>
	</div>

<script type="text/javascript">


function loadCompanyData(params, loadUrl){
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
			companySelectCallBack(index,row);
		} 
	}); 
	//分页加载
	$("#enttb").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function companySelectCallBack(index,row){
	var userType = $("#userType").val();
	var mobile = $("#memberMobile").val();
	if(userType == '2' || mobile == '' ){
	$("#memberMobile").val(row.companyMobile);
	$("#memberName").html(row.companyContact).attr("style", "color:green;");
	}
	$("#memberId").val(row.memberId);
	$("#companyName").val(row.companyName);
	$("#userMobile").val(row.companyMobile);
	
	$('#entDialog').dialog('close');
}

//查询按钮,根据name查询
$('#icon-search-car-select').click(function(){ 
	var params = {
	 "entName" : $("#entName").val()
	};
	loadCompanyData(params, CONTEXT+'consignment/queryEnt');
});	

loadCompanyData( {entName: ''}, CONTEXT+'consignment/queryEnt');
</script>


