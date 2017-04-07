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
		<title>看板数据管理</title>
	</head>
<body>
	<table id="boarddg" title="">
	</table>
	<div id="boardtb" style="padding:5px;height:auto">
		<form id="boardSearchForm" method="post">
		<div>
			看板名称: 
			<input type="text" id="name" class="easyui-validatebox" name="name" style="width:150px" >
			所属看板数据分类:
			<select name="menuId" id="menuId" style="width: 150px;">
				<option value="">请选择看板数据分类</option>
				<c:forEach items="${menuList}" var="menu" varStatus="s">
					<option value="${menu.menuID }">${menu.menuName }</option>
				</c:forEach>
			</select>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='icon-reload'>重置</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		</form>
		
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
	$("#boarddg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#boarddg').datagrid({
		url:CONTEXT+'board/query',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#boardtb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#boarddg").datagrid('clearSelections');
		},
		columns:[[
			{field:'name',title:'数据名称',width:200,align:'center' },
			{field:'menuId',title:'看板数据分类',width:100,align:'center'},
			{field:'valueTime',title:'数据更新时间',width:100,align:'center'},
			{field:'state',title:'状态',width:50,align:'center',formatter:formatterState },
			{field:'opt',title:'操作',width:50,align:'center',formatter:optformat}
		]]
	}); 
}

//状态
function formatterState(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "启用";
		}else if(str=="2"){
			return "禁用";
		}
	}else{
		return "未知";
	}
}

//查看
function detailObj(id){
	$('#detailDialog').dialog({'title':'查看DTO','href':CONTEXT+'board/detailById/'+id,'width': 800,'height': 500}).dialog('open');
}

function optformat(value,row,index){
	var tempval = "";
	if("1" == row.state){
		tempval = "禁用";
	}else if("2" == row.state){
		tempval = "启用";
	}
	return "<a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a>&nbsp;" +
	"<a class='operate' href='javascript:;' onclick=editObj('"+row.id+"','"+row.state+"');>"+tempval+"</a>";
}

//删除操作
function editObj(id,state){
	if("1" == state){
		state = "2";
	}else if("2" == state){
		state = "1";
	}
	jQuery.post(CONTEXT+"board/edit/"+id+"-"+state,function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#boarddg').datagrid('reload');
			$('#boarddg').datagrid("uncheckAll");
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
			"menuId" : $("#menuId").val()
			};
		loadList(params);
	});
	
	//新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增数据','href':CONTEXT+'push/boardAdd', 'width': 800,'height': 500}).dialog('open');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#boardSearchForm')[0].reset();
		$("#boarddg").datagrid('load', {});
	});
	
	//重置按钮
	$('#icon-reload').click(function(){
		$('#boardSearchForm')[0].reset();
	});
});
</script>
</html>