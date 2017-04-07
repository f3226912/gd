<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
<table id="businessdg" title="">
</table>
<div id="businesstb" style="padding:5px;height:auto">
	<form id="businessSearchForm" method="post">
		<div>
			&nbsp;创建时间&nbsp;: 
		    <input  type="text"  id="business_startDate" name="business_startDate"  
				onFocus="WdatePicker({onpicked:function(){business_startDate.focus();},maxDate:'#F{$dp.$D(\'business_endDate\')}'})"   
				onClick="WdatePicker({onpicked:function(){business_startDate.focus();},maxDate:'#F{$dp.$D(\'business_endDate\')}'})"    
				style="width:150px" >~
			<input  type="text"    id="business_endDate" name="business_endDate"   
				onFocus="WdatePicker({onpicked:function(){business_endDate.focus();},minDate:'#F{$dp.$D(\'business_startDate\')}'})"   
				onClick="WdatePicker({onpicked:function(){business_endDate.focus();},minDate:'#F{$dp.$D(\'business_startDate\')}'})"
				style="width:150px">
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-business-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='$("#businessSearchForm").reset()'>重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick='location.reload(true)'>刷新</a>
		</div>
	</form>
</div>

<script type="text/javascript">

function loadBusinessData(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#businessdg').datagrid({
		url: loadUrl,
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#businesstb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
			{field:'businessId',title:'',width:80,checkbox:true},
			{field:'userId',title:'会员ID',width:100,align:'center'},
			{field:'shopsName',title:'商铺名称',width:100,align:'center'},
			{field:'shopsDesc',title:'商铺简介',width:100,align:'center'},
			{field:'name',title:'公司名称',width:100,align:'center'},
			{field:'businessModel',title:'经营模式',width:100,align:'center'},
			{field:'staffCount',title:'员工数',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
			}}
		]],
		onDblClickRow: function(index,row){
			businessCallback(index, row);
		}
	}); 
	//分页加载
	$("#businessdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$(document).ready(function(){

	loadBusinessData(null, CONTEXT+'product/queryBusiness');
	// 查询按钮,根据name查询
	$('#icon-search-business-select').click(function(){ 
		var params = {
			"name" : $('#businessSearchForm #marketName').val(),
			"startDate":$('#businessSearchForm #startDate').val(),
			"endDate":$('#businessSearchForm #endDate').val()
		}
		loadBusinessData(params, CONTEXT+'product/queryBusiness');
	});	
});
</script>


