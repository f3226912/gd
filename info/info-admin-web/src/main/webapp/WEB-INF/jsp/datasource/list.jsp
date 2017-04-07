<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="农批网,公告信息,谷登农批网公告"/>
		<meta http-equiv="description" content="谷登农批网重大信息公告中心！"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>数据源管理</title>
	</head>
<body>
	<table id="datasourcedg" title="">
	</table>
	<div id="datasourcetb" style="padding:5px;height:auto">
		<div id="detailDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="#dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#datasourcedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datasourcedg').datagrid({
		url:CONTEXT+'datasource/query',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datasourcetb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#datasourcedg").datagrid('clearSelections');
		},
		columns:[[
			{field:'name',title:'数据名称',width:200,align:'center' },
			{field:'frequency',title:'更新频率',width:50,align:'center',formatter:formatterStyle},
			{field:'mode',title:'接入方式',width:50,align:'center',formatter:formatterState },
			{field:'lastUpdateTime',title:'最后更新时间',width:100,align:'center' },
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
}

//接入方式
function formatterState(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "文件导入";
		}else if(str=="2"){
			return "API";
		}
	}else{
		return "未知";
	}
}
//更新频率
function formatterStyle(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "每分钟";
		}else if(str=="2"){
			return "每小时";
		}else if(str=="3"){
			return "每天";
		}else if(str=="4"){
			return "每周";
		}else if(str=="5"){
			return "每月";
		}
	}else{
		return "未知";
	}
}

//查看
function detailObj(id){
	if(1 == id){
		$('#detailDialog').dialog({'title':'查看数据源','href':CONTEXT+'datasource/detailByWhbszyhls/'+id,'width': 1000,'height': 400}).dialog('open');
	}else if(2 == id){
		$('#detailDialog').dialog({'title':'查看数据源','href':CONTEXT+'datasource/detailByBaidu/'+id,'width': 1000,'height': 400}).dialog('open');
	}else if(3 == id){
		$('#detailDialog').dialog({'title':'查看数据源','href':CONTEXT+'datasource/detailByProOperate/'+id,'width': 1000,'height': 400}).dialog('open');
	}
}

function optformat(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a>";
}

//删除操作
function editObj(id,state){
	if("1" == state){
		state = "2";
	}else if("2" == state){
		state = "1";
	}
	jQuery.post(CONTEXT+"datasource/edit/"+id+"-"+state,function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#datasourcedg').datagrid('reload');
			$('#datasourcedg').datagrid("uncheckAll");
		}else{
			warningMessage(data);
			return;
		}
	});
}

$(document).ready(function(){
	initList();
	//查询按钮
	$('#icon-search').click(function(){ 
		var params = {
			"name" : $("#name").val(),
			"menuID" : $("#menuID").val()
			};
		loadList(params);
	});
	
	//新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增数据','href':CONTEXT+'push/datasourceAdd', 'width': 800,'height': 500}).dialog('open');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#datasourceSearchForm')[0].reset();
		$("#datasourcedg").datagrid('load', {});
	});
	
	//重置按钮
	$('#icon-reload').click(function(){
		$('#datasourceSearchForm')[0].reset();
	});
});
</script>
</html>