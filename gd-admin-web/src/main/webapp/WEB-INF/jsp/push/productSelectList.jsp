<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<table id="productdg" title="">
	</table>
	<div id="producttb" style="padding:5px;height:auto">
		<form id="productSearchForm" method="post">
		<div>
			<input type="hidden" id="categoryId" value="${categoryId}" />
			<input type="hidden" id="selectType" value="${selectType}" />
			用户名称: <input  type="text" id="account_q" name="account" style="width:150px" data-options="prompt:'请输入名称">&nbsp;
			商品名称: <input  type="text" id="productName_q" name="productName" style="width:150px" data-options="prompt:'请输入名称">&nbsp;<br>
			发布时间 :
			<input  type="text"  id="pstartDate" name="startDate"  
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"   
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    
				style="width:150px" >~
			<input  type="text" id="pendDate" name="endDate"   
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px"><br>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search2">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick="fromreset('productSearchForm');">重置</a>
		</div>
		</form>
	</div>

<script type="text/javascript">

function loadProList(params){
	params = !params ? {}: params;
	//数据加载
	$('#productdg').datagrid({
		url:CONTEXT+'push/getProductList',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#producttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'productName',title:'商品名称',width:100,align:'center'},
			{field:'cateName',title:'商品类目',width:100,align:'center'},
			{field:'realName',title:'真实姓名',width:100,align:'center'},
			{field:'account',title:'会员账号',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'},
			{field:'infoLifeDay',title:'信息有效期',width:100,align:'center'},
			{field:'state',title:'审核状态',width:100,align:'center', formatter: formatState},
			
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=productSelect('"+row.productId+"')>选择产品</a>";
			}}
		]]
	});
	//分页加载
	$("#productdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function productSelect(id){
	var selectType = $("#selectType").val();
	if(selectType == '1'){
		$("#productId").val(id);
		$("#proview").html("已选产品:id:" + id);
		$("#adLinkUrl").val("${webhomeUrl}product/"+id);
		$("#proview").show();
		$("#proDialog").dialog("close");
	}else{
		$("#productId2").val(id);
		$("#proview2").html("已选产品:id:" + id);
		$("#adLinkUrl2").val("${webhomeUrl}product/"+id);
		$("#proview2").show();
		$("#proDialog").dialog("close");
	}
}

function formatState(value,row,index){
	if (row.state == 0){
		return "草稿";
	}else if (row.state == 1){
		return "待审核";
	}else if (row.state == 2){
		return "审核不通过";
	}else if (row.state == 3){
		return "已上架";
	}else if (row.state == 4){
		return "已下架";
	}else if (row.state == 5){
		return "已删除";
	}else if (row.state == 6){
		return "历史版本";
	}
}

function fromreset(fromid){
	document.getElementById(fromid).reset();
}

$(document).ready(function(){
	$("#proview").hide();
	var paramsz = {
			"categoryId" : $("#categoryId").val()
			};
	loadProList(paramsz);
	//查询按钮
	$('#icon-search2').click(function(){
		var params = {
			"account":$("#account_q").val(),
			"productName" : $("#productName_q").val(),
			"categoryId" : $("#categoryId").val(),
			"startDate" : $("#pstartDate").val(),
			"endDate" : $("#pendDate").val()
			};
		loadProList(params);
	});
});
</script>

