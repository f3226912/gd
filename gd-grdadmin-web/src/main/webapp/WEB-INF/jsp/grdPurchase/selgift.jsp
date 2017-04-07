<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
.selectedgift{
    float: left;
    display: block;
    border: solid 1px #666;
    padding: 5px;
    margin-left:5px;
    margin-top:10px;
}
</style>
<table id="selgift_datagrid-table" title=""></table>
<div id="selgift_datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="purchase_datagrid-form" method="post">
	<div>
		礼品编码：
		<input type="text" id="selgift_giftNo" name="giftNo" placeholder="请输入礼品编码" class="easyui-validatebox" style="width:150px" >		
		礼品名称：
		<input type="text" id="selgift_giftName" name="giftName" placeholder="请输入礼品名称" class="easyui-validatebox" style="width:150px" >		
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-selgift-search" >查询</a>
		<input type="hidden" id="selgiftId" value="" /> 
	
	</div>
	</form>
</div>	

<div id="selectedgift_div">
<span>已选择的礼品：</span><br/>
</div>



<script>
$(function(){
	//获取上级页面已经选择的礼品id
	$("#selgiftId").val($("#seledgiftId").val());
	
	loadSelList();
	
	$('#btn-selgift-search').click(function(){
		query();
	});
	
})


function query(){
	var params = {
		"giftNo" : $("#selgift_giftNo").val(),
		"name" : $("#selgift_giftName").val(),
	};
	loadSelList(params);
	//重启导出功能
	disableExport = false ;
}



//初始化加载页面列表
function initSelList(){
	loadSelList(null);
	//分页加载
	$("#selgift_datagrid-table").datagrid("getPager").pagination({
		pageList: [50,100,200,300,500]
	});
	
	
}

function loadSelList(params){
	params = !params ? {}: params;
	//数据加载
	$('#selgift_datagrid-table').datagrid({
		url:CONTEXT+'grdGdGift/queryGift',
		width: 'auto',  
		queryParams: params,
		height: '350', 
		nowrap:true,
		toolbar:'#selgift_datagrid-tool-bar',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:false,
		fit:false,
		singleSelect:false,
		onLoadSuccess:function(){
			$("#selgift_datagrid-table").datagrid('clearSelections');
		},
		columns:[[
		    //{field:'id',title:'序号',width:20,checkbox:false},
		    {field:'giftNo',title:'礼品编号',width:150,align:'left'},
			{field:'name',title:'礼品名称',width:200,align:'left'},
			{field:'unit',title:'单位',width:60,align:'center'},
			{field:'remarks',title:'备注',width:60,align:'center'},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optselgift}
		]]
	}); 
}


function optselgift(value,row,index){
	var selgiftIds= $("#selgiftId").val();
	var arrIds = selgiftIds.split(",");
	var html="";
	var giftName = $.trim(row.name);
	if($.inArray(row.giftNo+"",arrIds)>=0){
		html+='<a class="operate" id="choose'+row.id+'" href="javascript:;" onclick=selGift("'+row.id+'","'+row.giftNo+'","'+giftName+'");>已选择</a>';
		var temp='<div id="'+row.giftNo+'" class="selectedgift">('+row.giftNo+')'+row.name+'&nbsp;&nbsp;<a href="javascript:delselgift('+row.id+','+row.giftNo+'); ">删除</a></div>';
		if($("#"+row.giftNo).text()==null || $("#"+row.giftNo).text()==""){
			$("#selectedgift_div").append(temp);
		}
	}else{
		html+='<a class="operate" id="choose'+row.id+'" href="javascript:;" onclick="selGift(\''+row.id+'\',\''+row.giftNo+'\',\''+giftName+'\')";>选择</a>';
	}
	return html;
}



function selGift(id,giftNo,name){
	var giftNo= giftNo+"";
	var html='<div id="'+giftNo+'" class="selectedgift">('+giftNo+')'+name+'&nbsp;&nbsp;<a href="javascript:delselgift('+id+','+giftNo+'); ">删除</a></div>';
	var giftNosArr= $("#selgiftId").val();
	if(giftNosArr==null||giftNosArr==""){
		$("#selectedgift_div").append(html);
		$("#selgiftId").val(giftNo)
		$("#choose"+id).text("已选择");
		return false;
	}
	
	var arrGiftNos = giftNosArr.split(",");
	if($.inArray(giftNo,arrGiftNos)>=0){
		warningMessage("已选择该礼品");
	}else{
		$("#selectedgift_div").append(html);
		$("#selgiftId").val(giftNosArr+","+giftNo)
		$("#choose"+id).text("已选择");
	}
	
}

function delselgift(id,giftNo){
	var id = id+"";
	var giftNo = giftNo+"";
	var selgiftIds= $("#selgiftId").val();
	var arrIds = selgiftIds.split(",");
	//obj是删除的那项
	var obj = arrIds.splice($.inArray(giftNo,arrIds),1);
	var newIds = arrIds.join(",");
	$("#selgiftId").val(newIds)
	$("#"+giftNo).remove();
	$("#choose"+id).text("选择");
	
}




</script>