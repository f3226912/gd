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
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>广告位</title>
		<style type="text/css">
			.redFont{
				color:red;
			}
		</style>
		<link href="${CONTEXT}css/uploader.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
	</head>
<body>
	<table id="adSpacedg"></table>
	<div id="adinfotb" style="padding:5px;height:auto">
		<form id="adSpaceSearchForm" method="post">
			<div>
				广告类型:
				<select name="adType" id="adTypeList" style="width: 150px;">
					<option value="">---请选择---</option>
					<option value="01">轮播图</option>
					<option value="02">产品推送</option>
					<option value="03">副图</option>
					<option value="04">全国农贸市场图</option>
				</select>
				广告位状态:
				<select name="state" id="stateList" style="width: 150px;">
					<option value="">---请选择---</option>
					<option value="1">启用</option>
					<option value="2">禁用</option>
				</select>
				广告渠道:
				<select name="adCanal" id="adCanalList" style="width: 150px;">
					<option value="">---请选择---</option>
					<option value="01">农批web</option>
					<option value="02">农商友</option>
					<option value="03">农速通</option>
				</select>		
				所属市场:
				<select name="marketId" id="marketIdList" style="width: 150px;">
					<option value="">---请选择---</option>
					<c:forEach items="${marketList}" var="market" varStatus="s">
						<option value="${market.id }">${market.marketName }</option>
					</c:forEach>
				</select>
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a>
			<a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a>
		</div>
		
		<div id="addAdSpaceDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveAdd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addAdSpaceDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editAdSpaceDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveEdit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editAdSpaceDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="detailDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="#dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="proDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsPro">
			<div id="#dlg-buttonsPro" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#proDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function editObj(id){
	$('#editAdSpaceDialog').dialog({'title':'编辑数据','href':CONTEXT+'adSpace/edit?id='+id, 'width': 800,'height': 500}).dialog('open');	
}

function delObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"adSpace/delete",{"id":id},function(data){
				if (data.status == 0){
					slideMessage("操作成功！");
				}else{
					warningMessage(data.message);
					return;
				}
    		});
		}
	});
}

function detailObj(id){
	$('#detailDialog').dialog({'title':'查看数据','href':CONTEXT+'adSpace/view?id='+id, 'width': 800,'height': 500}).dialog('open');
}

function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#adSpacedg').datagrid({
		url:CONTEXT+'adSpace/query',
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#adinfotb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#adinfodg").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:0,checkbox:true},
			{field:'spaceSign',title:'广告位',width:100,align:'center'},
			{field:'adCanal',title:'广告渠道',width:100,align:'center' },
			{field:'adType',title:'广告类型',width:100,align:'center',formatter:adTypeFormatter },
			{field:'marketName',title:'广告所属市场',width:100,align:'center'},
			{field:'cityName',title:'所属城市（物流）',width:100,align:'center'},
			{field:'adPrice',title:'价格',width:100,align:'center'},
			{field:'adSize',title:'广告图规格',width:100,align:'center'},
			{field:'state',title:'状态',width:100,align:'center',formatter:stateFormatter },
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
}

function canalFormatter(val, row){
	if(val == "1"){
		return "谷登农批";
	}else if(val == "2"){
		return "农商友";
	}else if(val == "3"){
		return "农批友";
	}else if(val == "4"){
		return "农速通";
	}else if(val == "5"){
		return "供应商";
	}else{
		return "";
	}
}

function adTypeFormatter(val, row){
	if(val == "1"){
		return "图片";
	}else if(val == "2"){
		return "文本";
	}else if(val == "3"){
		return "app启动页";
	}else{
		return "";
	}
}

function stateFormatter(val, row){
	if(val=="1"){
		return "启用";
	}else if(val=="2"){
		return "禁用";
	}else{
		return "";
	}
}	

function optformat(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a>&nbsp;" +
	"<a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a>&nbsp;" +
	"<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
}

function initList(){
	loadList(null);
	//分页加载
	$("#adSpacedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$(document).ready(function(){
	//查询按钮
	$('#icon-search').click(function(){
		var params = {};
		loadList(params);
	});
	
	//新增
	$('#icon-add').click(function(){
		$('#addAdSpaceDialog').dialog({'title':'新增数据','href':CONTEXT+'adSpace/add?menuId=1', 'width': 800,'height': 500}).dialog('open');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#adSpaceSearchForm')[0].reset();
		$("#adinfodg").datagrid('load', {});
	});
	
	//重置按钮
	$('#icon-reload').click(function(){
		$('#adSpaceSearchForm')[0].reset();
	});
	initList();
});
</script>
</html>