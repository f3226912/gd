<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	
<table id="giftdg" title=""></table>
<div id="gifttb" style="padding:5px;height:auto">
	<form id="giftSearchForm" method="post">
	<input type="hidden" value="${activityId }" id="activityId"/>
	<div>
	       礼品名称: 
		<input  type="text" id="giftName" name="giftName"  style="width:150px" >
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-gift">查询</a>&nbsp;&nbsp;
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='resetForm()'>重置</a>
	</div>
	</form>
	<input type="hidden" id="status" name="status" value="${status}">
</div>
<script type="text/javascript">
$(document).ready(function(){
	loadMemberData({activityId: $("#activityId").val()}, CONTEXT+'actGiftExchange/queryGiftList');
});

function loadMemberData(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#giftdg').datagrid({
		url: loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#gifttb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'giftId',title:'',width:0,hidden:true},
			{field:'giftName',title:'礼品名称',width:100,align:'center'},
			{field:'cost',title:'库存量',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optFormatter},
		]],
		onDblClickRow: function(index,row){
			giftCallBack(index,row);
		}
	}); 
	
	//分页加载
	$("#giftdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function optFormatter(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=\"selectGiftCallBack("+row.giftId+",'"+row.giftName+"');\">选择</a>";
}

$('#icon-search-gift').click(function(){ 
	var params = {
		"activityId" : $("#giftSearchForm #activityId").val(),
		"giftName" : $("#giftSearchForm #giftName").val()
	}
	loadMemberData(params, CONTEXT+'actGiftExchange/queryGiftList');
});	

//重置按钮
function resetForm(){
	$("#giftSearchForm #giftName").val("");
}

//回调
function giftCallBack(index,row){
	$("#addActGiftExchangeForm #showGiftDailog").val(row.giftName);
	$("#addActGiftExchangeForm #giftId").val(row.giftId);
	$('#giftDialog').dialog('close');
}

//回调
function selectGiftCallBack(giftId, giftName){
	$("#addActGiftExchangeForm #showGiftDailog").val(giftName);
	$("#addActGiftExchangeForm #giftId").val(giftId);
	$('#giftDialog').dialog('close');
}
</script>